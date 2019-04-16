/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
 */

package fms_server.requests;

public class LoginRequest extends Request {
    /**
     * userName of the user attempting to login
     */
    protected final String userName;
    /**
     * Password of the user attempting to login, un-hashed
     */
    protected final String password;

    /**
     * Constructor for the login request
     * @param userName userName of the user attempting to login not null
     * @param password password of the user attempting to login not null
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getuserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
