/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.services;

import fms_server.dao.DataBaseException;
import fms_server.dao.EventDAO;
import fms_server.dao.IDatabaseAccessObject;
import fms_server.dao.ModelNotFoundException;
import fms_server.logging.Logger;
import fms_server.models.AuthToken;
import fms_server.models.Event;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.EventRequest;
import fms_server.results.EventResult;
import fms_server.results.EventsResult;

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
        AuthToken token = new AuthToken(request.getToken());
        List<Event> list;
        try {
            list = ((EventDAO) getDao()).getAllFromDescendant(token.getUserName());
            Event[] listr = new Event[list.size()];
            listr = list.toArray(listr);
            return new EventsResult(!list.isEmpty(), "", listr);
        } catch (DataBaseException | ModelNotFoundException e) {
            Logger.error("Something went wrong getting the list of events", e);
            return new EventsResult(false, "Failed to get list of events", null);
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

        AuthToken token = new AuthToken(request.getToken());
        Event event = null;
        try {
            event = ((EventDAO) getDao()).get(request.getEventID());
            if (!event.getDescendant().equals(token.getUserName()))
                return new EventResult(false, "Could not find the model", null);
        } catch (ModelNotFoundException | DataBaseException e) {
            Logger.error("Something went wrong, could not find the model", e);
        }
        return new EventResult(event != null, event == null ? "Could not find the model" : "Got it", event);
    }
}
