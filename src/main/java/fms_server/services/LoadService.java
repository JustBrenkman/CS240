package fms_server.services;

import fms_server.dao.*;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.LoadRequest;
import fms_server.results.LoadResult;

import javax.xml.crypto.Data;

/**
 * Load service class
 */
public class LoadService extends Service {
    EventDAO eventDAO;
    UserDAO userDAO;
    PersonDAO personDAO;

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
    }

    /**
     * Loads database with user, person, and event objects
     * @param request contains list of users, events, and persons
     * @return if fill was successful or not
     */
    public LoadResult load(LoadRequest request) {
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
//                return new LoadResult(false, "Unable to load the data");
            }
        return new LoadResult(allLoaded, allLoaded ? "Loaded everything into the databases" : "Unable to load the data");
    }
}
