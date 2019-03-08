/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:20 PM
 */

package fms_server;

import fms_server.exceptions.DataBaseException;
import fms_server.requests.RegisterRequest;
import fms_server.services.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegisterServiceTest extends ServiceTest {
    private RegisterService service;

    RegisterServiceTest() {
        service = new RegisterService(userDAO, personDAO, eventDAO);
    }

    @Test
    @DisplayName("Register pass")
    void registerPass() {
        Assertions.assertTrue(service.register(new RegisterRequest("JustBrenkman", "ix002225", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m")).isSuccess());
    }

    @Test
    @DisplayName("Register fail null pointer")
    void registerFailUsername() {
        Assertions.assertFalse(service.register(new RegisterRequest(null, "ix002225", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m")).isSuccess());
    }

    @Test
    @DisplayName("Register fail null pointer")
    void registerFailEmail() {
        Assertions.assertFalse(service.register(new RegisterRequest("JustBrenkman", "ix002225", null, "Ben", "Brenkman", "m")).isSuccess());
    }

    @Test
    @DisplayName("Register fail null pointer")
    void registerFailPassword() throws DataBaseException {
        Assertions.assertFalse(service.register(new RegisterRequest("JustBrenkman", null, "JustBrenkman@gmail.com", "Ben", "Brenkman", "m")).isSuccess());
    }
}
