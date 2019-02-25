package fms_server.requests;

public class FillRequest extends Request {
    /**
     * Username of the fill request
     */
    private final String userName;
    /**
     * Number of generations to fill
     */
    private final int generations;

    /**
     * Fill request constructor
     * @param userName username of the person to fill
     * @param generations number of generations to fill
     */
    public FillRequest(String userName, int generations) {
        this.userName = userName;
        this.generations = generations;
    }

    public String getUserName() {
        return userName;
    }

    public int getGenerations() {
        return generations;
    }
}
