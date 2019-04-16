/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
 */

package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.dao.ModelNotFoundException;
import fms_server.dao.PersonDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.exceptions.NotAuthenticatedException;
import fms_server.logging.Logger;
import fms_server.models.AuthToken;
import fms_server.models.Person;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.PersonRequest;
import fms_server.results.PersonResult;
import fms_server.results.PersonsResult;

import java.util.List;

/**
 * Person Service class
 */
public class PersonService extends Service {

    /**
     * Person Service constructor
     * @param dao PersonDAO
     */
    public PersonService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Get a list of all Persons
     * @param request This request must be authenticated
     * @return return a list of all person objects in the database
     */
    public PersonsResult getAllPersons(AuthenticatedRequest request) throws NotAuthenticatedException {
        if (authenticateToken(request.getToken()))
            throw new NotAuthenticatedException();

        AuthToken token = new AuthToken(request.getToken());

        List<Person> list;
        try {
            list = ((PersonDAO) getDao()).getAllFromDescendant(token.getuserName());
            Person[] listr = new Person[list.size()];
            listr = list.toArray(listr);
            return new PersonsResult(!list.isEmpty(), "", listr);
        } catch (DataBaseException | ModelNotFoundException e) {
            Logger.error("Something went wrong getting the list of persons", e);
        }
        return null;
    }

    /**
     * Gets a single person based on an id
     * @param request contains id information, and authentication
     * @return a person object
     */
    public PersonResult getPerson(PersonRequest request) throws NotAuthenticatedException {
        if (authenticateToken(request.getAuthToken()))
            throw new NotAuthenticatedException();
        Person person = null;
        try {
            person = ((PersonDAO) getDao()).get(request.getPersonID());
            AuthToken token = new AuthToken(request.getAuthToken());
            if (!person.getDescendant().equals(token.getuserName()))
                return new PersonResult(false, "Could not find the model", null);
        } catch (DataBaseException | ModelNotFoundException e) {
            return new PersonResult(false, "Could not find the model", null);
        }

        return new PersonResult(true, "Got em", person);
    }
}
