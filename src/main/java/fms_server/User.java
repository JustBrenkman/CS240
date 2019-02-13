package fms_server;

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private char gender;
    private int peronID;

    /**
     * User constructor
     * @param username - username of the user
     * @param password - password for the user hashed
     * @param email - email of the user
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @param gender - gender of user
     * @param peronID - person id of the user
     */
    public User(String username, String password, String email, String firstName, String lastName, char gender, int peronID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.peronID = peronID;
    }

    /**
     * Getter for user username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username - username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password, function can also hash the password if not already hashed
     * @param password - password, hashed or not
     * @param musthash - param to tell the setter to hash the given password or not
     */
    public void setPassword(String password, boolean musthash) {
        this.password = password;
    }

    /**
     * Getter for email of user
     * @return - user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for user's email
     * @param email - email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for user's first name
     * @return - user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for user's first name
     * @param firstName - user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for user's last name
     * @return - last name of user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for user's last name
     * @param lastName - last name of user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for user's gender
     * @return - gender of user
     */
    public char getGender() {
        return gender;
    }

    /**
     * Setter for user's gender
     * @param gender - user's gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Getter for user's person id
     * @return - person if of user
     */
    public int getPeronID() {
        return peronID;
    }

    /**
     * Setter for user's person id
     * @param peronID - peron's id of user
     */
    public void setPeronID(int peronID) {
        this.peronID = peronID;
    }
}
