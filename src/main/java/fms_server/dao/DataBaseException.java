package fms_server.dao;

public class DataBaseException extends Exception {
    /**
     * Type of failure
     */
    private ERROR_TYPE type;

    /**
     * Possible database failure types
     */
    public enum ERROR_TYPE {SQL, CONNECTION_ALREADY_IN_USE, UNKNOWN, CLOSED_CONNECTION}

    public DataBaseException() {
        super();
        this.type = ERROR_TYPE.UNKNOWN;
    }

    public DataBaseException(String message, ERROR_TYPE type) {
        super(message);
        this.type = type;
    }

    public DataBaseException(ERROR_TYPE type) {
        this.type = type;
    }

    public DataBaseException(String message) {
        super(message);
        this.type = ERROR_TYPE.UNKNOWN;
    }

    public ERROR_TYPE getType() {
        return type;
    }
}
