/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:42 PM
 */

package fms_server.services;

import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.RegisterRequest;
import fms_server.results.LoginResult;
import fms_server.results.RegisterResult;
import fms_server.results.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RegisterService extends Service {
    private PersonDAO personDAO;
    private EventDAO eventDAO;

    /**
     * Constructor for the register service
     * @param userDAO UserDAO
     */
    public RegisterService(UserDAO userDAO, PersonDAO personDAO, EventDAO eventDAO) {
        super(userDAO);
        this.personDAO = personDAO;
        this.eventDAO = eventDAO;
    }

    /**
     * Registers user, creates a new person and user model to add to the database
     * @param user RegisterRequest
     * @return A RegisterResult that contains the information about the result of the request
     */
    public Result register(RegisterRequest user) {
        try {
            Person person = new Person(user);
            User userToAdd = new User(person.getId(), user);
            if (user.getUsername() == null)
                return new RegisterResult(false, "Bad request, username needs to be lowercase", null);
            personDAO.add(person);
            getDao().add(userToAdd);
            createRandomInfo(user, person); // Fills in 4 generations
            return new LoginResult(true, "Successfully register user", generateAuthToken(userToAdd).getAuthTokenString(), user.getUsername(), person.getId());
        } catch (Exception e) {
            Logger.error("Unable to add user", e);
            return new RegisterResult(false, "Already a user, or missing information", null);
        }
    }

    /**
     * Creates random information
     *
     * @param user   user
     * @param person person
     * @throws DataBaseException if something goes wrong
     */
    private void createRandomInfo(RegisterRequest user, Person person) throws DataBaseException {
        List<Event> events = new ArrayList<>();

        FillService.Generator.setUser(user.getUsername());
        Person spouse = FillService.Generator.generateSpouse(person);
        List<Person> people = FillService.Generator.generateGenerations(Arrays.asList(person, spouse), 4, events, 2019 - 35);

        // Create events for couple
        HashMap<String, Person> map = new HashMap<>();
        map.put("mother", spouse);
        map.put("father", person);
        List<Event> coupleEvents = FillService.Generator.generateEventsForCouple(map, 2019);

        personDAO.add(spouse);
        personDAO.addAll(people);
        eventDAO.addAll(events);
        eventDAO.addAll(coupleEvents);
    }
}
