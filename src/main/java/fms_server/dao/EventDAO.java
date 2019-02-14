package fms_server.dao;

import fms_server.models.Event;

import java.util.List;
import java.util.Map;

/**
 * Event Database Access Object
 */
public class EventDAO implements DatabaseAccessObject<Event, Integer> {
    /**
     * Gets an event object from database
     * @param id Identifier of object
     * @return Event object
     */
    @Override
    public Event get(Integer id) {
        return null;
    }

    /**
     * Gets all event objects in database
     * @return List of Event's
     */
    @Override
    public List<Event> getAll() {
        return null;
    }

    /**
     * Adds new Event object to database
     * @param event Event object to add
     */
    @Override
    public void add(Event event) {

    }

    /**
     * Update an event object
     * @param event event object to add
     */
    @Override
    public void update(Event event) {

    }

    /**
     * Checks to see if an event object exists
     * @param id identifier of the object
     * @return boolean
     */
    @Override
    public boolean doesExist(Integer id) {
        return false;
    }

    /**
     * Deletes an object from the database
     * @param id identifier of the object
     */
    @Override
    public void drop(Integer id) {

    }

    /**
     * Filters Events
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return List of filtered events
     */
    @Override
    public List<Event> filter(Map<String, Object> queries) {
        return null;
    }
}
