package fms_server.results;

public class FillResult extends Result {
    /**
     * Constructor for result class
     *
     * @param success whether or not request was successful
     * @param message message about result
     */
    public FillResult(boolean success, String message) {
        super(success, message);
    }
}
