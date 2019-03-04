/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.services;

public class NotAuthenticatedException extends Exception {
    public NotAuthenticatedException() {
    }

    public NotAuthenticatedException(String message) {
        super(message);
    }
}
