/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.requests;

public class LoginRequest extends Request {
    /**
     * Username of the user attempting to login
     */
    protected final String username;
    /**
     * Password of the user attempting to login, un-hashed
     */
    protected final String password;

    /**
     * Constructor for the login request
     * @param userName username of the user attempting to login not null
     * @param password password of the user attempting to login not null
     */
    public LoginRequest(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
