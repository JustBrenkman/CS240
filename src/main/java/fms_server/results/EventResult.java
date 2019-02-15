package fms_server.results;

public class EventResult extends Result {
    /**
     * Name of user account this event belongs to
     */
    private final String descendant;
    /**
     * Event’s unique ID
     */
    private final int eventID;
    /**
     * ID of the person this event belongs to
     */
    private final int personID;
    /**
     * Latitude of the event’s location
     */
    private final int latitude;
    /**
     * Longitude of the event’s location
     */
    private final int longitude;
    /**
     * Name of country where event occurred
     */
    private final String country;
    /**
     * Name of city where event occurred
     */
    private final String city;
    /**
     * Type of event
     */
    private final String eventType;
    /**
     * Year the event occurred
     */
    private final int year;

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
    public EventResult(boolean success, String message, String descendant, int eventID, int personID, int latitude, int longitude, String country, String city, String eventType, int year) {
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
}
