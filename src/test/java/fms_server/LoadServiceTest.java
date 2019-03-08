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
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.LoadRequest;
import fms_server.services.LoadService;
import org.junit.jupiter.api.*;

public class LoadServiceTest {
    private static PersonDAO personDAO;
    private static UserDAO userDAO;
    private static EventDAO eventDAO;

    static {
        personDAO = new PersonDAO();
        eventDAO = new EventDAO();
        userDAO = new UserDAO();
    }

    private LoadService service;

    LoadServiceTest() {
        service = new LoadService(new EventDAO(), new UserDAO(), new PersonDAO());
    }

    @BeforeAll
    static void setup() throws DataBaseException {
        personDAO.clear();
        eventDAO.clear();
        userDAO.clear();
    }

    @AfterAll
    static void shutdown() throws DataBaseException {
        personDAO.clear();
        eventDAO.clear();
        userDAO.clear();
    }

    @Test
    @DisplayName("Load service pass")
    void loadPass() {
        Assertions.assertTrue(service.load(new LoadRequest(new Person[]{
                new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3")
        }, new User[]{
                new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1")
        }, new Event[]{
                new Event("1", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019)
        })).isSuccess());
    }

    @Test
    @DisplayName("Load service pass")
    void loadPass2() {
        Assertions.assertTrue(service.load(new LoadRequest(new Person[]{
                new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3"),
                new Person("2", "JustBrenkman1", "Ben", "Brenkman", "m", "1", "2", "3")
        }, new User[]{
                new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1"),
                new User("JustBrenkman1", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail1.com", "Ben", "Brenkman", "m", "2")
        }, new Event[]{
                new Event("1", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019),
                new Event("2", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019)
        })).isSuccess());
    }

    @Test
    @DisplayName("Load service fail, duplicate data")
    void loadFailDuplicate() {
        Assertions.assertFalse(service.load(new LoadRequest(new Person[]{
                new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3")
        }, new User[]{
                new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1")
        }, new Event[]{
                new Event("1", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019),
                new Event("1", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019)
        })).isSuccess());
    }
}
