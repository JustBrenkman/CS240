package fms_server.services;

import fms_server.dao.*;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.EventRequest;
import fms_server.results.EventResult;
import fms_server.results.EventsResult;
import fms_server.results.PersonsResult;

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
    public EventsResult getEventList(AuthenticatedRequest request) throws NotAuthenticatedException {
        if (authenticateToken(request.getToken()))
            throw new NotAuthenticatedException();

        List<Event> list;
        try {
            list = ((EventDAO) getDao()).getAll();
            Event[] listr = new Event[list.size()];
            listr = list.toArray(listr);
            EventsResult result = new EventsResult(!list.isEmpty(), "", listr);
            return result;
        } catch (DataBaseException | ModelNotFoundException e) {
            Logger.error("Something went wrong getting the list of events", e);
            return null;
        }
    }

    /**
     * Gets an event object from the database
     * @param request EventRequest contains event id that we want
     * @return an event object
     */
    public EventResult getEvent(EventRequest request) throws NotAuthenticatedException {
        if (authenticateToken(request.getToken()))
            throw new NotAuthenticatedException();

        Event event = null;
        try {
            event = ((EventDAO) getDao()).get(request.getEventID());
        } catch (ModelNotFoundException | DataBaseException e) {
            Logger.error("Something went wrong, could not find the model", e);
        }
        return new EventResult(event != null, event == null ? "Could not find the model" : "Got it", event);
    }
}
