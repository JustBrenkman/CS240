package com.cs240.imageeditor;

class FailedProcessImageException extends RuntimeException {
    FailedProcessImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
