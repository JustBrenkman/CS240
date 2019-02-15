package fms_server.models;

/**
 * Person model class
 */
public class Person extends IModel<Integer> {
    /**
     * User (Username) to which this person belongs
     */
    private String descendant;
    /**
     * Person’s first name (non-empty string)
     */
    private String firstName;
    /**
     * Person’s last name (non-empty string)
     */
    private String lastName;
    /**
     *  Person’s gender (char: “f” or “m”)
     */
    private char gender;
    /**
     * ID of person’s father (possibly null)
     */
    private int fatherID;
    /**
     * Mother: ID of person’s mother (possibly null)
     */
    private int motherID;
    /**
     * Spouse: ID of person’s spouse (possibly null)
     */
    private int spouseID;

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
    public Person(int personID, String descendant, String firstName, String lastName, char gender, int fatherID, int motherID, int spouseID) {
        this.setId(personID);
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Getter for the person id
     * @return - person id
     */
    public int getPersonID() {
        return this.getId();
    }

    /**
     * Setter for person id
     * @param personID - person id
     */
    public void setPersonID(int personID) {
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
    public char getGender() {
        return gender;
    }

    /**
     * Setter for the gender of the person
     * @param gender - person's gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Getter for father's id
     * @return - id of the father
     */
    public int getFatherID() {
        return fatherID;
    }

    /**
     * Setter for the father
     * @param fatherID - father's id
     */
    public void setFatherID(int fatherID) {
        this.fatherID = fatherID;
    }

    /**
     * Getter for mother's id
     * @return - mother's id
     */
    public int getMotherID() {
        return motherID;
    }

    /**
     * Setter for mother's id
     * @param motherID - mother's id
     */
    public void setMotherID(int motherID) {
        this.motherID = motherID;
    }

    /**
     * Getter for spouse's id
     * @return spouse's id
     */
    public int getSpouseID() {
        return spouseID;
    }

    /**
     * Setter for spouse's id
     * @param spouseID - spouse's id
     */
    public void setSpouseID(int spouseID) {
        this.spouseID = spouseID;
    }
}
