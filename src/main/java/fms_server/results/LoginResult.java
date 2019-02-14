package fms_server.results;

import fms_server.models.AuthToken;

/**
 * Holds the login attempt information
 * Information is readonly,
 */
public final class LoginResult {
    private final boolean isSuccessful;
    private final AuthToken authToken;

    /**
     * Constructor for LoginResult
     * @param isSuccessful true if user if found and passwords match
     * @param authToken this is a server generated token that contains encrypted information about the user.
     *                  If this token is modified outside the server it will be nulled
     */
    public LoginResult(boolean isSuccessful, AuthToken authToken) {
        this.isSuccessful = isSuccessful;
        this.authToken = authToken;
    }

    /**
     * True is the login attempted was successful
     * @return whether or not the login attempt was successful
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
