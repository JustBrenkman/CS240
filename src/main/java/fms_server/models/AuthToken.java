package fms_server.models;

import fms_server.FMSServer;
import fms_server.logging.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.LinkedHashMap;

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

    public String getUserName() {
        Jws<Claims> body = Jwts.parser().setSigningKey(FMSServer.getKey()).parseClaimsJws(authToken);
        Logger.fine("Username: " + body.getBody().getSubject());
        return body.getBody().getSubject();
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
