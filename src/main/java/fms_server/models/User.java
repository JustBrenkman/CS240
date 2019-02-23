package fms_server.models;

import com.google.common.hash.Hashing;
import fms_server.requests.RegisterRequest;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * User model class
 */
public class User extends AbstractModel<String> {
    /**
     * Username variable
     */
    protected String username;
    /**
     * Password variable, should be hashed
     */
    protected String password;
    /**
     * Email variable, should be a valid email address
     */
    protected String email;
    /**
     * First name of user
     */
    protected String firstName;
    /**
     * Last name of the user
     */
    protected String lastName;
    /**
     * Gender of user
     */
    protected String gender;

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
    public User(String username, String password, String email, String firstName, String lastName, String gender, String peronID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.setId(peronID);
    }

    /**
     * This is a protected constructor, used only for converting database entries to models
     */
    protected User() {
        super();
    }

    public User(String personId, RegisterRequest request) {
        this.setId(personId);
        this.username = request.getUsername();
        this.password = Hashing.sha256().hashString(request.getPassword(), StandardCharsets.UTF_8).toString();
        this.email = request.getEmail();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.gender = String.valueOf(request.getGender());
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
    public String getGender() {
        return gender;
    }

    /**
     * Setter for user's gender
     * @param gender - user's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter for user's person id
     * @return - person if of user
     */
    public String getPeronID() {
        return this.getId();
    }

    /**
     * Setter for user's person id
     * @param personID - peron's id of user
     */
    public void setPeronID(String personID) {
        this.setId(personID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return gender.equals(user.gender) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, email, firstName, lastName, gender);
    }
}
