/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
 */

package fms_server.models;

import fms_server.requests.RegisterRequest;

import java.util.UUID;

/**
 * Person model class
 */
public class Person extends AbstractModel<String> {
    /**
     * User (userName) to which this person belongs
     */
    protected String descendant;
    /**
     * Person’s first name (non-empty string)
     */
    protected String firstName;
    /**
     * Person’s last name (non-empty string)
     */
    protected String lastName;
    /**
     *  Person’s gender (char: 'f' or 'm')
     */
    protected String gender;
    /**
     * ID of person’s father (possibly null)
     */
    protected String father;
    /**
     * Mother: ID of person’s mother (possibly null)
     */
    protected String mother;
    /**
     * Spouse: ID of person’s spouse (possibly null)
     */
    protected String spouse;

    protected String personID;

    /**
     * Constructor for the person object
     * @param personID - person id
     * @param descendant - descendent name
     * @param firstName - first name of the person
     * @param lastName - last name of the person
     * @param gender - gender of the person
     * @param father - father id number of the person
     * @param mother - mother id of the person
     * @param spouse - spouse id of the person
     */
    public Person(String personID, String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        this.setId(personID);
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public Person(RegisterRequest request) {
        this.setId(UUID.randomUUID().toString());
        this.descendant = request.getuserName();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.gender = request.getGender();
    }

    protected Person() {}

    /**
     * Getter for the person id
     * @return - person id
     */
    public String getPersonID() {
        if (this.getId() == null)
            return this.personID;
        return this.getId();
    }

    /**
     * Setter for person id
     * @param personID - person id
     */
    public void setPersonID(String personID) {
        this.setId(personID);
        this.personID = personID;
    }

    /**
     * Getter for person's descendant
     * @return - descendant name
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * Setter for descendant
     * @param descendant - descendant name
     */
    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    /**
     * Getter for persons's first name
     * @return - first name of person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for person's first name
     * @param firstName - first name of person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the person's last name
     * @return - last name of person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for person's last name
     * @param lastName - last name of person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the gender of the person
     * @return - gender of the person
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter for the gender of the person
     * @param gender - person's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter for father's id
     * @return - id of the father
     */
    public String getfather() {
        return father;
    }

    /**
     * Setter for the father
     * @param father - father's id
     */
    public void setfather(String father) {
        this.father = father;
    }

    /**
     * Getter for mother's id
     * @return - mother's id
     */
    public String getmother() {
        return mother;
    }

    /**
     * Setter for mother's id
     * @param mother - mother's id
     */
    public void setmother(String mother) {
        this.mother = mother;
    }

    /**
     * Getter for spouse's id
     * @return spouse's id
     */
    public String getspouse() {
        return spouse;
    }

    /**
     * Setter for spouse's id
     * @param spouse - spouse's id
     */
    public void setspouse(String spouse) {
        this.spouse = spouse;
    }
}
