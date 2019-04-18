/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/17/19 8:20 PM
 */

package fms_server.models;

import java.util.Objects;

/**
 * Event model class
 */
public class Event extends AbstractModel<String> {
    /**
     * Name of user account this event belongs to
     */
    protected String descendant;
    /**
     * ID of the person this event belongs to
     */
    protected String personID;
    /**
     * Latitude of the event’s location
     */
    protected double latitude;
    /**
     * Longitude of the event’s location
     */
    protected double longitude;
    /**
     * Name of country where event occurred
     */
    protected String country;
    /**
     * Name of city where event occurred
     */
    protected String city;
    /**
     * Type of event
     */
    protected String eventType;
    /**
     * Year the event occurred
     */
    protected int year;

    protected String eventID;

    /**
     * Event constructor
     * @param eventID - Unique id to the event
     * @param descendant - name of the descendant
     * @param personID unique id of a Person attributed to the event
     * @param latitude - event latitude
     * @param longitude - event longitude
     * @param country - country that event occurred in
     * @param city - city that event occurred in
     * @param eventType - type of event, "death", "birth" etc.
     * @param year - year that
     */
    public Event(String eventID, String descendant, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.setId(eventID);
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.eventID = eventID;
    }

    protected Event() {
        super();
    }

    /**
     * Getter for the event id
     * @return - event id
     */
    public String getEventID() {
        if (eventID != null)
            return eventID;
        return this.getId();
    }

    /**
     * Setter for the even tid
     * @param eventID - event id
     */
    public void setEventID(String eventID) {
        this.setId(eventID);
        this.eventID = eventID;
    }

    /**
     * Getter for descendant
     * @return - descendent
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * Setter for defendant
     * @param descendant - descendant name
     */
    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    /**
     * Getter for Person id
     * @return - Person id
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Setter for Person id
     * @param personID - Person id
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * getter for event latitude
     * @return - event latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter for event latitude
     * @param latitude - event latitude
     */
    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for event longitude
     * @return - event longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Getter for event longitude
     * @param longitude - event longitude
     */
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter for event country location
     * @return - country where event occurred
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for country location for the event
     * @param country - event country location
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for city location
     * @return - city location of event
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for city location
     * @param city - city location of event
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter for event type
     * @return - event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Setter for event type
     * @param eventType - event type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Getter for the year of the event
     * @return - year of the event
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter of the year of the event
     * @param year - year of the event
     */
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return getEventID() +
                ", " + getPersonID() +
                ", " + getDescendant() +
                ", " + getLatitude() +
                ", " + getLongitude() +
                ", " + getCountry() +
                ", " + getCity() +
                ", " + getEventType() +
                ", " + getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        if (!super.equals(o)) return false;
        Event event = (Event) o;
        return Double.compare(event.latitude, latitude) == 0 &&
                Double.compare(event.longitude, longitude) == 0 &&
                year == event.year &&
                descendant.equals(event.descendant) &&
                personID.equals(event.personID) &&
                country.equals(event.country) &&
                city.equals(event.city) &&
                eventType.equals(event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descendant, personID, latitude, longitude, country, city, eventType, year);
    }
}
