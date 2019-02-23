package fms_server.requests;

import fms_server.models.AuthToken;

public class EventRequest extends AuthenticatedRequest {
    /**
     * ID of the event that we want information about
     */
    private final int eventID;

    /**
     * Constructor for authentication request
     *
     * @param token  authentication token
     * @param eventID id of the event that is in question
     */
    public EventRequest(AuthToken token, int eventID) {
        super(token.getAuthTokenString());
        this.eventID = eventID;
    }

    public int getEventID() {
        return eventID;
    }
}
