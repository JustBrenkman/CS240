package fms_server.requests;

import fms_server.models.*;

public class LoadRequest extends Request {
    /**
     * List of person objects to load
     */
    protected final Person[] persons;
    /**
     * List of user objects to load
     */
    protected final User[] users;
    /**
     * List of event objects to load
     */
    protected final Event[] events;

    /**
     * Constructor for load request
     * @param persons List of person objects to load
     * @param users List of user objects to load
     * @param events List of event objects to load
     */
    public LoadRequest(Person[] persons, User[] users, Event[] events) {
        this.persons = persons;
        this.users = users;
        this.events = events;
    }

    public Person[] getPersons() {
        return persons;
    }

    public User[] getUsers() {
        return users;
    }

    public Event[] getEvents() {
        return events;
    }
}
