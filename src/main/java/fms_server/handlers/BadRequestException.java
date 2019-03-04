package fms_server.handlers;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
