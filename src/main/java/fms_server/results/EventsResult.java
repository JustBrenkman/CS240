package fms_server.results;

import fms_server.models.Event;

/**
 * Event Result class
 */
public class EventsResult extends Result {
    /**
     * List of events
     */
    private final Event[] data;
    /**
     * Constructor for result class
     *  @param success whether or not request was successful
     * @param message message about result
     * @param data list of events to return
     */
    public EventsResult(boolean success, String message, Event[] data) {
        super(success, message);
        this.data = data;
    }
}
