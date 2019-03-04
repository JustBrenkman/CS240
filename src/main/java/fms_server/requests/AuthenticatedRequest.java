package fms_server.requests;

public class AuthenticatedRequest {
    /**
     * Authentication token
     */
    private final String token;

//    /**
//     * Additional parameters
//     */
//    private final Map<String, Object> params;

    /**
     * Constructor for authentication request
     * @param token authentication token
//     * @param params additional parameters in request
     */
    public AuthenticatedRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

//    public Map<String, Object> getParams() {
//        return params;
//    }
}
