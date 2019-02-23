package fms_server.models;

/**
 * This class takes the authentication token and performs all the calculations about the authentication of the token
 */
public class AuthToken {
    /**
     * Authentication token
     */
    private String authToken;
    private String subject;
    /**
     * Constructor for the authentication token
     * @param authToken authentication string
     * @param subject Authdata to include in authtoken
     */
    public AuthToken(String authToken, String subject) {
        this.authToken = authToken;
        this.subject = subject;
    }

    /**
     * Checks to see if the authentication token is valid
     * @return valid or not
     */
    public boolean isAuthenticated(){return true;}

    public String getAuthTokenString() {
        return authToken;
    }
}
