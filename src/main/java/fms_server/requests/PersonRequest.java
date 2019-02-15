package fms_server.requests;

public class PersonRequest {
    /**
     * Authentication token
     */
    private final String authToken;
    /**
     * Person ID to get information about
     */
    private final int personID;

    /**
     * Constructor for the person request
     * @param authToken authentication token
     * @param personID person id
     */
    public PersonRequest(String authToken, int personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getPersonID() {
        return personID;
    }
}
