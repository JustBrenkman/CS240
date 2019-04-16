/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
 */

package fms_server.requests;

public class RegisterRequest extends Request {
    /**
     * User Name of the user being registered, not null
     */
    protected final String userName;
    /**
     * Password un hashed of the user, not null
     */
    protected final String password;
    /**
     * Email address, not null
     */
    protected final String email;
    /**
     * First name of the user, not ull
     */
    protected final String firstName;
    /**
     * Last name of the user, not null
     */
    protected final String lastName;
    /**
     * Gender of the person, not null
     */
    protected final String gender;

    /**
     * Constructor of the register request
     * @param userName userName not null
     * @param password password un-hashed not null
     * @param email email address not null
     * @param firstName first name of the user not null
     * @param lastName last name of the user not null
     * @param gender gender of the user not null
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getuserName() {
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

    public String getGender() {
        return gender;
    }
}
