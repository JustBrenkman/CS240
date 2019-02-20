package fms_server.models;

public class ModelDoesNotFitException extends Exception {
    public ModelDoesNotFitException() {
    }

    public ModelDoesNotFitException(String message) {
        super(message);
    }
}
