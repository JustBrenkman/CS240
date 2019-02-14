package fms_server.results;

/**
 * Clear result class used to respond to an HttpExchange
 */
public class ClearResult extends Result {

    /**
     * Constructor for result class
     *
     * @param success whether or not request was successful
     * @param message message about result
     */
    public ClearResult(boolean success, String message) {
        super(success, message);
    }
}
