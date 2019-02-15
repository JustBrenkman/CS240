package fms_server.results;

import fms_server.models.Person;

public class PersonsResult extends Result {
    /**
     * List of Person object
     */
    private final Person[] data;

    /**
     * Constructor for result class
     *  @param success whether or not request was successful
     * @param message message about result
     * @param data list of person objects to return
     */
    public PersonsResult(boolean success, String message, Person[] data) {
        super(success, message);
        this.data = data;
    }

    public Person[] getData() {
        return data;
    }
}
