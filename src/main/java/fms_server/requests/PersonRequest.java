/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.requests;

public class PersonRequest {
    /**
     * Authentication token
     */
    private String authToken;
    /**
     * Person ID to get information about
     */
    private final String personID;

    /**
     * Constructor for the person request
     * @param authToken authentication token
     * @param personID person id
     */
    public PersonRequest(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPersonID() {
        return personID;
    }
}
