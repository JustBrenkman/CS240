/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
 */

package fms_server.services;

import com.google.common.hash.Hashing;
import fms_server.dao.ModelNotFoundException;
import fms_server.dao.UserDAO;
import fms_server.exceptions.DataBaseException;
import fms_server.logging.Logger;
import fms_server.models.User;
import fms_server.requests.LoginRequest;
import fms_server.results.LoginResult;

import java.nio.charset.StandardCharsets;

/**
 * Service class to do with logging in
 */
public class LoginService extends Service {

    /**
     * Constructor for the login service
     * @param dao UserDAO
     */
    public LoginService(UserDAO dao) {
        super(dao);
    }

    /**
     * Attempts to login the user.
     * @param request LoginRequest contains userName, email, password
     * @return LoginResult holds the information about the attempt. If it was unsuccessful will may return a null object
     */
    public LoginResult login(LoginRequest request) {
        try {
            User user = ((UserDAO) getDao()).getUserByUserName(request.getuserName());
            Logger.info("Retrieved user: " + user.toString());
            // Check passwords
            if (Hashing.sha256().hashString(request.getPassword(), StandardCharsets.UTF_8).toString().equals(user.getPassword())) {
                return new LoginResult(true, "Found user", generateAuthToken(user).getAuthTokenString(), user.getuserName(), user.getPersonID());
            }
        } catch (DataBaseException | ModelNotFoundException e) {
            Logger.warn("Unable to login user", e);
            return new LoginResult(false, "Failed to find user", null, null, null);
        }
        return new LoginResult(false, "Passwords don't match", null, null, null);
    }
}
