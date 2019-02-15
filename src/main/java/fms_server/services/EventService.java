package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.models.Event;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.EventRequest;

import java.util.List;

/**
 * Event service class
 */
public class EventService extends Service {
    /**
     * Event service constructor
     * @param dao EventDAO
     */
    public EventService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Gets a list of events in the database
     * @param request authentication request, must be authenticated
     * @return list of event objects
     */
    public List<Event> getEventList(AuthenticatedRequest request) {
        return null;
    }

    /**
     * Gets an event object from the database
     * @param request EventRequest contains event id that we want
     * @return an event object
     */
    public Event getEvent(EventRequest request) {
        return null;
    }
}
