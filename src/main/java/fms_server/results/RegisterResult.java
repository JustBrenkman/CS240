package fms_server.results;

import fms_server.models.AuthToken;

/**
 * This is the register result class
 */
public class RegisterResult extends Result {
    private final AuthToken authToken;

    /**
     * Creates new Register result
     * @param token authentication token
     */
    public RegisterResult(boolean success, String message, AuthToken token) {
        super(success, message);
        this.authToken = token;
    }

    /**
     * Gets the authentication token
     * @return AuthToken
     */
    public AuthToken getAuthToken() {
        return authToken;
    }
}
