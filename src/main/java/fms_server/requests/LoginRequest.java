package fms_server.requests;

public class LoginRequest {
    /**
     * Username of the user attempting to login
     */
    private final String username;
    /**
     * Password of the user attempting to login, un-hashed
     */
    private final String password;

    /**
     * Constructor for the login request
     * @param userName username of the user attempting to login not null
     * @param password password of the user attempting to login not null
     */
    public LoginRequest(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
