/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:20 PM
 */

package fms_server;

import fms_server.dao.DataBase;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.exceptions.NotAuthenticatedException;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.EventRequest;
import fms_server.results.ClearResult;
import fms_server.results.Result;
import fms_server.services.ClearService;
import fms_server.services.EventService;
import org.junit.jupiter.api.*;

public class EventServiceTest {
    private ClearService service;
    EventDAO eventDAO;

    EventServiceTest() {
        service = new ClearService(new UserDAO(), new PersonDAO(), new UserDAO());
    }

    @BeforeAll
    static void setup() throws DataBaseException {
        Logger.setUpLogSaver();
        Logger.setLogClass(true);
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.head("Testing Service test cases");
        DataBase.createTables();
    }

    @BeforeEach
    void clear() throws DataBaseException {
        eventDAO = new EventDAO();
        eventDAO.clear();
    }


    //******************************************************************************************************************
    //                                                  EVENTS
    //******************************************************************************************************************

    @Test
    @DisplayName("PASS Clear service")
    void clearAll() {
        ClearResult result = service.clear(null);
        if (result.isSuccess())
            Logger.pass("Passed clear all");
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("PASS Event service get event")
    void getEventPass() {
        try {
            eventDAO.add(new Event(
                    "test_event",
                    "JustBrenkman",
                    "773h-dfag-sdf4-sasx",
                    111.2655,
                    46.678,
                    "USA",
                    "Provo",
                    "birth",
                    2019
                    ));
            EventService service = new EventService(new EventDAO());
            Result result = service.getEvent(
                    new EventRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString(), "test_event"));
            Logger.pass("Passed get event");
            Assertions.assertTrue(result.isSuccess());
        } catch (DataBaseException | NotAuthenticatedException e) {
            Logger.fail("Failed to get event");
        }
    }

    @Test
    @DisplayName("FAILED Event service get event, not authenticated")
    void getEventFailAuth() {
        try {
            eventDAO.add(new Event(
                    "test_event",
                    "JustBrenkman",
                    "773h-dfag-sdf4-sasx",
                    111.2655,
                    46.678,
                    "USA",
                    "Provo",
                    "birth",
                    2019
            ));
            EventService service = new EventService(new EventDAO());
            Assertions.assertThrows(NotAuthenticatedException.class, () -> {
                try {
                    service.getEvent(
                            new EventRequest("kjasjkdhasjkdhh", "test_event"));
                }catch (NotAuthenticatedException e) {
                    Logger.pass("Passed get event not auth");
                    throw e;
                }
            });
        } catch (DataBaseException e) {
            Logger.fail("Failed to get event");
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("PASS Event service get non existent event")
    void getEventNotTherePass() {
        try {
            eventDAO.add(new Event(
                    "test_event",
                    "JustBrenkman",
                    "773h-dfag-sdf4-sasx",
                    111.2655,
                    46.678,
                    "USA",
                    "Provo",
                    "birth",
                    2019
            ));
            EventService service = new EventService(new EventDAO());
            Result result = service.getEvent(
                    new EventRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString(), "test_event1"));
            Logger.pass("Passed get non existent event");
            Assertions.assertFalse(result.isSuccess());
        } catch (DataBaseException | NotAuthenticatedException e) {
            Logger.fail("Failed to get event");
        }
    }


    //******************************************************************************************************************
    //                                             MULTIPLE EVENTS
    //******************************************************************************************************************


    @Test
    @DisplayName("PASS Event service get event")
    void getEventsPass() {
        try {
            eventDAO.add(new Event(
                    "test_event",
                    "JustBrenkman",
                    "773h-dfag-sdf4-sasx",
                    111.2655,
                    46.678,
                    "USA",
                    "Provo",
                    "birth",
                    2019
            ));
            EventService service = new EventService(new EventDAO());
            Result result = service.getEvent(
                    new EventRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString(), "test_event"));
            Logger.pass("Passed get event");
            Assertions.assertTrue(result.isSuccess());
        } catch (DataBaseException | NotAuthenticatedException e) {
            Logger.fail("Failed to get event");
        }
    }

    @Test
    @DisplayName("FAILED Event service get event, not authenticated")
    void getEventsFailAuth() {
        try {
            eventDAO.add(new Event(
                    "test_event",
                    "JustBrenkman",
                    "773h-dfag-sdf4-sasx",
                    111.2655,
                    46.678,
                    "USA",
                    "Provo",
                    "birth",
                    2019
            ));
            EventService service = new EventService(new EventDAO());
            Assertions.assertThrows(NotAuthenticatedException.class, () -> {
                try {
                    service.getEvent(
                            new EventRequest("kjasjkdhasjkdhh", "test_event"));
                }catch (NotAuthenticatedException e) {
                    Logger.pass("Passed get event not auth");
                    throw e;
                }
            });
        } catch (DataBaseException e) {
            Logger.fail("Failed to get event");
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("PASS Event service get non existent events")
    void getEventsNotTherePass() {
        try {
            EventService service = new EventService(new EventDAO());
            Result result = service.getEventList(new AuthenticatedRequest(service.generateAuthToken("JustBrenkman").getAuthTokenString()));
            Logger.pass("Passed get non existent event");
            Assertions.assertFalse(result.isSuccess());
        } catch (NotAuthenticatedException e) {
            Logger.fail("Failed to get event");
        }
    }
}
