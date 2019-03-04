/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.requests;

public class EventRequest extends AuthenticatedRequest {
    /**
     * ID of the event that we want information about
     */
    private final String eventID;

    /**
     * Constructor for authentication request
     *
     * @param token  authentication token
     * @param eventID id of the event that is in question
     */
    public EventRequest(String token, String eventID) {
        super(token);
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }
}
