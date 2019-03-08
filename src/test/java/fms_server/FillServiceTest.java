/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:20 PM
 */

package fms_server;

import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.FillRequest;
import fms_server.results.FillResult;
import fms_server.services.FillService;
import org.junit.jupiter.api.*;

public class FillServiceTest {
    private static PersonDAO personDAO;
    private static UserDAO userDAO;
    private static EventDAO eventDAO;

    static {
        personDAO = new PersonDAO();
        eventDAO = new EventDAO();
        userDAO = new UserDAO();
    }

    private FillService service;

    FillServiceTest() {
        service = new FillService(new EventDAO(), new UserDAO(), new PersonDAO());
    }

    @BeforeAll
    static void setup() throws DataBaseException {
        personDAO.clear();
        eventDAO.clear();
        userDAO.clear();
        personDAO.add(new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3"));
        userDAO.add(new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1"));
    }

    @AfterAll
    static void shutdown() throws DataBaseException {
        personDAO.clear();
        eventDAO.clear();
        userDAO.clear();
    }

    @Test
    @DisplayName("Fill Service PASS 4 generations")
    void fillPass4() {
        Assertions.assertAll(() -> {
            FillResult result = service.fill(new FillRequest("JustBrenkman", 4));
            Assertions.assertTrue(result.isSuccess());
            Assertions.assertTrue(result.getMessage().contains("30 persons"));
            Assertions.assertTrue(result.getMessage().contains("90 events"));
        });
    }

    @Test
    @DisplayName("Fill Service PASS 2 generations")
    void fillPass2() {
        Assertions.assertAll(() -> {
            FillResult result = service.fill(new FillRequest("JustBrenkman", 2));
            Assertions.assertTrue(result.isSuccess());
            Assertions.assertTrue(result.getMessage().contains("6 persons"));
            Assertions.assertTrue(result.getMessage().contains("18 events"));
        });
    }

    @Test
    @DisplayName("Fill fail wrong username")
    void fillFail() throws DataBaseException {
        Assertions.assertFalse(service.fill(new FillRequest("wrong", 2)).isSuccess());
    }
}
