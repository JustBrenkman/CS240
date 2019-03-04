package fms_server.models;

import fms_server.FMSServer;
import fms_server.logging.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

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

    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Decodes auth token and gets username
     *
     * @return username in auth token
     */
    public String getUserName() {
        Jws<Claims> body = Jwts.parser().setSigningKey(FMSServer.getKey()).parseClaimsJws(authToken);
        Logger.fine("Username: " + body.getBody().getSubject());
        return body.getBody().getSubject();
    }

    public String getAuthTokenString() {
        return authToken;
    }
}
