/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.models;

public class ModelDoesNotFitException extends Exception {
    public ModelDoesNotFitException() {
    }

    public ModelDoesNotFitException(String message) {
        super(message);
    }
}
