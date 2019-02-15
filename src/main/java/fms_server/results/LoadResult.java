package fms_server.results;

public class LoadResult extends Result {
    /**
     * Constructor for result class
     *
     * @param success whether or not request was successful
     * @param message message about result
     */
    public LoadResult(boolean success, String message) {
        super(success, message);
    }
}
