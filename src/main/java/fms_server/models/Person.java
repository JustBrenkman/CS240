/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.models;

import fms_server.requests.RegisterRequest;

import java.util.UUID;

/**
 * Person model class
 */
public class Person extends AbstractModel<String> {
    /**
     * User (Username) to which this person belongs
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
    protected String fatherID;
    /**
     * Mother: ID of person’s mother (possibly null)
     */
    protected String motherID;
    /**
     * Spouse: ID of person’s spouse (possibly null)
     */
    protected String spouseID;

    /**
     * Constructor for the person object
     * @param personID - person id
     * @param descendant - descendent name
     * @param firstName - first name of the person
     * @param lastName - last name of the person
     * @param gender - gender of the person
     * @param fatherID - father id number of the person
     * @param motherID - mother id of the person
     * @param spouseID - spouse id of the person
     */
    public Person(String personID, String descendant, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.setId(personID);
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public Person(RegisterRequest request) {
        this.setId(UUID.randomUUID().toString());
        this.descendant = request.getUsername();
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
        return this.getId();
    }

    /**
     * Setter for person id
     * @param personID - person id
     */
    public void setPersonID(String personID) {
        this.setId(personID);
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
    public String getFatherID() {
        return fatherID;
    }

    /**
     * Setter for the father
     * @param fatherID - father's id
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    /**
     * Getter for mother's id
     * @return - mother's id
     */
    public String getMotherID() {
        return motherID;
    }

    /**
     * Setter for mother's id
     * @param motherID - mother's id
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    /**
     * Getter for spouse's id
     * @return spouse's id
     */
    public String getSpouseID() {
        return spouseID;
    }

    /**
     * Setter for spouse's id
     * @param spouseID - spouse's id
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
