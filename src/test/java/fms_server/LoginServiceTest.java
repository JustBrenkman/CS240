/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:20 PM
 */

package fms_server;

import com.google.common.hash.Hashing;
import fms_server.dao.UserDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.LoginRequest;
import fms_server.services.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class LoginServiceTest extends ServiceTest {
    private LoginService service;

    LoginServiceTest() {
        service = new LoginService(new UserDAO());
    }

    @Test
    @DisplayName("Login pass")
    void loginPass() throws DataBaseException {
        personDAO.add(new Person("1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3"));
        userDAO.add(new User("JustBrenkman", Hashing.sha256().hashString("ix002225", StandardCharsets.UTF_8).toString(), "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1"));
        Assertions.assertTrue(service.login(new LoginRequest("JustBrenkman", "ix002225")).isSuccess());
    }

    @Test
    @DisplayName("Login fail, no such user")
    void loginFailUser() {
        Assertions.assertFalse(service.login(new LoginRequest("SomeRandomDude", "ix002225")).isSuccess());
    }

    @Test
    @DisplayName("Login fail, incorrect password")
    void loginFailPassword() {
        Assertions.assertFalse(service.login(new LoginRequest("JustBrenkman", "asdasd")).isSuccess());
    }
}
