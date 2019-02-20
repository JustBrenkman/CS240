package fms_server.dao;

public class ModelNotFoundException extends Exception {
    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException() {
    }
}
