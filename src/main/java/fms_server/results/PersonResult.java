/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.results;

import fms_server.models.Person;

public class PersonResult extends Result {
    /**
     * Descendant of person
     */
    private final String descendant;
    /**
     * Peron's id
     */
    private final String personID;
    /**
     * Person's first name
     */
    private final String firstName;
    /**
     * Person's last name
     */
    private final String lastName;
    /**
     * Person's gender
     */
    private final String gender;
    /**
     * Father's id
     */
    private final String fatherID;
    /**
     * Mother's ID
     */
    private final String motherID;
    /**
     * Spouse's id
     */
    private final String spouseID;


    /**
     * Constructor for result class
     *  @param success whether or not request was successful
     * @param message message about result
     * @param descendant name of defendant
     * @param personID person's id
     * @param firstName first name of person
     * @param lastName last name of person
     * @param gender gender of person
     * @param fatherID father's id can be null
     * @param motherID mother's id can be null
     * @param spouseID spouse's id can be null
     */
    public PersonResult(boolean success, String message, String descendant, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        super(success, message);
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public PersonResult(boolean success, String message, Person person) {
        super(success, message);
        if (person != null) {
            this.descendant = person.getDescendant();
            this.personID = person.getId();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.gender = person.getGender();
            this.fatherID = person.getFatherID();
            this.motherID = person.getMotherID();
            this.spouseID = person.getSpouseID();
        } else {
            this.descendant = null;
            this.personID = null;
            this.firstName = null;
            this.lastName = null;
            this.gender = null;
            this.fatherID = null;
            this.motherID = null;
            this.spouseID = null;
        }
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
