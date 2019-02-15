package fms_server.requests;

public class RegisterRequest {
    /**
     * User Name of the user being registered, not null
     */
    private final String userName;
    /**
     * Password un hashed of the user, not null
     */
    private final String password;
    /**
     * Email address, not null
     */
    private final String email;
    /**
     * First name of the user, not ull
     */
    private final String firstName;
    /**
     * Last name of the user, not null
     */
    private final String lastName;
    /**
     * Gender of the person, not null
     */
    private final char gender;

    /**
     * Constructor of the register request
     * @param userName username not null
     * @param password password un-hashed not null
     * @param email email address not null
     * @param firstName first name of the user not null
     * @param lastName last name of the user not null
     * @param gender gender of the user not null
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, char gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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
}
