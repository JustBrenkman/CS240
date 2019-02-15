package fms_server.requests;

import fms_server.models.AuthToken;

import java.util.Map;

public class AuthenticatedRequest {
    /**
     * Authentication token
     */
    private final AuthToken token;

//    /**
//     * Additional parameters
//     */
//    private final Map<String, Object> params;

    /**
     * Constructor for authentication request
     * @param token authentication token
//     * @param params additional parameters in request
     */
    public AuthenticatedRequest(AuthToken token) {
        this.token = token;
//        this.params = params;
    }

    public AuthToken getToken() {
        return token;
    }

//    public Map<String, Object> getParams() {
//        return params;
//    }
}
