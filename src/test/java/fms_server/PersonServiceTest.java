/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:20 PM
 */

package fms_server;

import fms_server.dao.ModelNotFoundException;
import fms_server.dao.PersonDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.exceptions.NotAuthenticatedException;
import fms_server.logging.Logger;
import fms_server.models.Person;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.PersonRequest;
import fms_server.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonServiceTest extends ServiceTest {
    PersonService service;

    PersonServiceTest() {
        service = new PersonService(new PersonDAO());
    }

    //******************************************************************************************************************
    //                                                  EVENTS
    //******************************************************************************************************************

    @Test
    @DisplayName("PASS Person service get person")
    void getEventPass() {
        try {
            personDAO.clear();
            personDAO.add(new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3"));
            Assertions.assertTrue(service.getPerson(new PersonRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString(), "1")).isSuccess());
        } catch (DataBaseException | NotAuthenticatedException e) {
            Logger.fail("Failed to get event");
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("FAILED Person service get person, not authenticated")
    void getEventFailAuth() {
        Assertions.assertThrows(NotAuthenticatedException.class, () -> service.getPerson(new PersonRequest("asdadafwrveververferf", "1")));
    }

    @Test
    @DisplayName("PASS Person service get non existent person")
    void getEventNotTherePass() throws NotAuthenticatedException, ModelNotFoundException, DataBaseException {
        Assertions.assertFalse(service.getPerson(new PersonRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString(), "5")).isSuccess());
    }


    //******************************************************************************************************************
    //                                             MULTIPLE EVENTS
    //******************************************************************************************************************


    @Test
    @DisplayName("PASS Person service get person list")
    void getPersonsPass() {
        try {
            personDAO.clear();
            personDAO.add(new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3"));
            Assertions.assertTrue(service.getAllPersons(new AuthenticatedRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString())).isSuccess());
        } catch (DataBaseException | NotAuthenticatedException e) {
            Assertions.fail();
            Logger.fail("Failed to get event");
        }
    }

    @Test
    @DisplayName("FAILED Person service get person, not authenticated")
    void getEventsFailAuth() {
        try {
            personDAO.clear();
            personDAO.add(new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3"));
            Assertions.assertThrows(NotAuthenticatedException.class, () -> service.getAllPersons(new AuthenticatedRequest("asdadsasd")));
        } catch (DataBaseException e) {
            Assertions.fail();
            Logger.fail("Failed to get event");
        }
    }

    @Test
    @DisplayName("PASS Person service get non existent people")
    void getEventsNotTherePass() {
        try {
            Assertions.assertTrue(service.getAllPersons(new AuthenticatedRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString())).isSuccess());
        } catch (NotAuthenticatedException e) {
            Assertions.fail();
            Logger.fail("Failed to get event");
        }
    }
}
