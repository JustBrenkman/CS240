/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.results;

/**
 * Holds the login attempt information
 * Information is readonly,
 */
public final class LoginResult extends Result{
    private final String authToken;
    private final String username;
    private final String personId;

    /**
     * Constructor for LoginResult
     * @param isSuccessful true if user if found and passwords match
     * @param authToken this is a server generated token that contains encrypted information about the user.
     * @param userName username of the person logging in
     * @param personId person ID of the user logging in
     * @param message message about how it went
     */
    public LoginResult(boolean isSuccessful, String message, String authToken, String userName, String personId) {
        super(isSuccessful, message);
        this.authToken = authToken;
        this.username = userName;
        this.personId = personId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonId() {
        return personId;
    }
}
