package imageeditor;

class FailedProcessImageException extends RuntimeException {
    FailedProcessImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
