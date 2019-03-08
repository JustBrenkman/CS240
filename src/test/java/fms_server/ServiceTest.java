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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class ServiceTest {
    static PersonDAO personDAO;
    static UserDAO userDAO;
    static EventDAO eventDAO;

    static {
        personDAO = new PersonDAO();
        eventDAO = new EventDAO();
        userDAO = new UserDAO();
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
}
