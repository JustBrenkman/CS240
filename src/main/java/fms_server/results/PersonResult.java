package fms_server.results;

public class PersonResult extends Result {
    /**
     * Descendant of person
     */
    private final String descendant;
    /**
     * Peron's id
     */
    private final int personID;
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
    private final char gender;
    /**
     * Father's id
     */
    private final int fatherID;
    /**
     * Mother's ID
     */
    private final int motherID;
    /**
     * Spouse's id
     */
    private final int spouseID;


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
    public PersonResult(boolean success, String message, String descendant, int personID, String firstName, String lastName, char gender, int fatherID, int motherID, int spouseID) {
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

    public String getDescendant() {
        return descendant;
    }

    public int getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public int getFatherID() {
        return fatherID;
    }

    public int getMotherID() {
        return motherID;
    }

    public int getSpouseID() {
        return spouseID;
    }
}
