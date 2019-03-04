/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.requests;

public class AuthenticatedRequest {
    /**
     * Authentication token
     */
    private final String token;

    /**
     * Constructor for authentication request
     * @param token authentication token
     */
    public AuthenticatedRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
