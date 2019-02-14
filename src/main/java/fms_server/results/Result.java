package fms_server.results;

/**
 * Base class for all messages
 */
public abstract class Result {
    private final boolean success;
    private final String message;

    /**
     * Constructor for result class
     * @param success whether or not request was successful
     * @param message message about result
     */
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
