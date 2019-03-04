/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 1:48 PM
 */

package fms_server.exceptions;

public class NotAuthenticatedException extends Exception {
    public NotAuthenticatedException() {}

    public NotAuthenticatedException(String message) {
        super(message);
    }
}
