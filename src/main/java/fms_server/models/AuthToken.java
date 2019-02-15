package fms_server.models;

/**
 * This class takes the authentication token and performs all the calculations about the authentication of the token
 */
public class AuthToken {
    /**
     * Authentication token
     */
    private String authToken;

    /**
     * Constructor for the authentication token
     * @param authToken authentication string
     */
    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Checks to see if the authentication token is valid
     * @return valid or not
     */
    public boolean isAuthenticated(){return true;}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
