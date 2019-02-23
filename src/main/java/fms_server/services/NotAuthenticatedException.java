package fms_server.services;

public class NotAuthenticatedException extends Exception {
    public NotAuthenticatedException() {
    }

    public NotAuthenticatedException(String message) {
        super(message);
    }
}
