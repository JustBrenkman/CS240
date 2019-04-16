/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
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
    private final String father;
    /**
     * Mother's ID
     */
    private final String mother;
    /**
     * Spouse's id
     */
    private final String spouse;


    /**
     * Constructor for result class
     *  @param success whether or not request was successful
     * @param message message about result
     * @param descendant name of defendant
     * @param personID person's id
     * @param firstName first name of person
     * @param lastName last name of person
     * @param gender gender of person
     * @param father father's id can be null
     * @param mother mother's id can be null
     * @param spouse spouse's id can be null
     */
    public PersonResult(boolean success, String message, String descendant, String personID, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        super(success, message);
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public PersonResult(boolean success, String message, Person person) {
        super(success, message);
        if (person != null) {
            this.descendant = person.getDescendant();
            this.personID = person.getPersonID();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.gender = person.getGender();
            this.father = person.getfather();
            this.mother = person.getmother();
            this.spouse = person.getspouse();
        } else {
            this.descendant = null;
            this.personID = null;
            this.firstName = null;
            this.lastName = null;
            this.gender = null;
            this.father = null;
            this.mother = null;
            this.spouse = null;
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

    public String getfather() {
        return father;
    }

    public String getmother() {
        return mother;
    }

    public String getspouse() {
        return spouse;
    }
}
