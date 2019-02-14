package fms_server.models;

/**
 * Event model class
 */
public class Event extends Model<Integer> {
    private  String descendant;
    private int personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

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
    public Event(int eventID, String descendant, int personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.setId(eventID);
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Getter for the event id
     * @return - event id
     */
    public int getEventID() {
        return this.getId();
    }

    /**
     * Setter for the even tid
     * @param eventID - event id
     */
    public void setEventID(int eventID) {
        this.setId(eventID);
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
    public int getPersonID() {
        return personID;
    }

    /**
     * Setter for Person id
     * @param personID - Person id
     */
    public void setPersonID(int personID) {
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
    public void setLatitude(double latitude) {
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
    public void setLongitude(double longitude) {
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
}
