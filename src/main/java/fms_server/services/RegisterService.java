/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.services;

import fms_server.dao.*;
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
    private IDatabaseAccessObject<Person, String> personDAO;
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
        Person person = new Person(user);
        List<Event> events = new ArrayList<>();
        User userToAdd = new User(person.getId(), user);
        try {
            if (user.getUsername() == null)
                return new RegisterResult(false, "Bad request, username needs to be lowercase", null);
            personDAO.add(person);
            getDao().add(userToAdd);
            FillService.Generator.setUser(user.getUsername());
            Person spouse = FillService.Generator.generateSpouse(person);
            List<Person> people = FillService.Generator.generateGenerations(Arrays.asList(person, spouse), 4, events, 2019 - 35);
            HashMap<String, Person> map = new HashMap<>();
            map.put("mother", spouse);
            map.put("father", person);
            List<Event> coupleEvents = FillService.Generator.generateEventsForCouple(map, 2019);
            personDAO.add(spouse);
            personDAO.addAll(people);
            eventDAO.addAll(events);
            eventDAO.addAll(coupleEvents);
        } catch (DataBaseException e) {
            Logger.error("Unable to add user", e);
            return new RegisterResult(false, "Already a user", null);
        }
        return new LoginResult(true, "Successfully register user", generateAuthToken(userToAdd).getAuthTokenString(), user.getUsername(), person.getId());
    }
}
