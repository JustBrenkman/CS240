/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.services;

import fms_server.exceptions.DataBaseException;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.LoadRequest;
import fms_server.results.LoadResult;

/**
 * Load service class
 */
public class LoadService extends Service {
    EventDAO eventDAO;
    UserDAO userDAO;
    PersonDAO personDAO;

    ClearService clearService;

    /**
     * Load service constructor
     * @param eventDAO IDatabaseObject
     * @param userDAO
     * @param personDAO
     */
    public LoadService(EventDAO eventDAO, UserDAO userDAO, PersonDAO personDAO) {
        super(eventDAO);
        this.eventDAO = eventDAO;
        this.userDAO = userDAO;
        this.personDAO = personDAO;
        clearService = new ClearService(eventDAO, userDAO, personDAO);
    }

    /**
     * Loads database with user, person, and event objects
     * @param request contains list of users, events, and persons
     * @return if fill was successful or not
     */
    public LoadResult load(LoadRequest request) {
        clearService.clear(null);
        for (User user : request.getUsers()) {
            user.hashPassword();
        }
        boolean allLoaded = true;
        try {
            for (Event event : request.getEvents())
                eventDAO.add(event);
            for (User user : request.getUsers())
                userDAO.add(user);
            for (Person person : request.getPersons())
                personDAO.add(person);
        } catch (DataBaseException | NullPointerException e) {
            Logger.warn("Unable to add data to database", e);
            allLoaded = false;
        }
        return new LoadResult(allLoaded, allLoaded ? "Loaded " + request.getPersons().length + " users, " + request.getPersons().length + " persons, " + request.getEvents().length + " events" + " into the databases" : "Unable to load the data");
    }
}
