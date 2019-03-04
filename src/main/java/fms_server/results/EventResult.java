/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.results;

import fms_server.models.Event;

public class EventResult extends Result {
    /**
     * Name of user account this event belongs to
     */
    private String descendant;
    /**
     * Event’s unique ID
     */
    private String eventID;
    /**
     * ID of the person this event belongs to
     */
    private String personID;
    /**
     * Latitude of the event’s location
     */
    private double latitude;
    /**
     * Longitude of the event’s location
     */
    private double longitude;
    /**
     * Name of country where event occurred
     */
    private String country;
    /**
     * Name of city where event occurred
     */
    private String city;
    /**
     * Type of event
     */
    private String eventType;
    /**
     * Year the event occurred
     */
    private int year;

    /**
     * Constructor for result class
     * @param success whether or not request was successful
     *  @param message message about result
     * @param descendant Name of user account this event belongs to
     * @param eventID Event’s unique ID
     * @param personID ID of the person this event belongs to
     * @param latitude Latitude of the event’s location
     * @param longitude Name of country where event occurred
     * @param country Name of city where event occurred
     * @param city Name of city where event occurred
     * @param eventType Type of event
     * @param year Year the event occurred
     */
    public EventResult(boolean success, String message, String descendant, String eventID, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        super(success, message);
        this.descendant = descendant;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public EventResult(boolean success, String message, Event event) {
        super(success, message);
        if (event != null) {
            this.descendant = event.getDescendant();
            this.eventID = event.getEventID();
            this.personID = event.getPersonID();
            this.latitude = event.getLatitude();
            this.longitude = event.getLongitude();
            this.country = event.getCountry();
            this.city = event.getCity();
            this.eventType = event.getEventType();
            this.year = event.getYear();
        }
    }
}
