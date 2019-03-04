/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.results;

import fms_server.models.AuthToken;

/**
 * This is the register result class
 */
public class RegisterResult extends Result {
    private final String authToken;

    /**
     * Creates new Register result
     * @param token authentication token
     * @param success successful or not
     * @param message message about how it went
     */
    public RegisterResult(boolean success, String message, AuthToken token) {
        super(success, message);
        this.authToken = (token == null)? null : token.getAuthTokenString();
    }

    /**
     * Gets the authentication token
     * @return AuthToken
     */
    public String getAuthToken() {
        return authToken;
    }
}
