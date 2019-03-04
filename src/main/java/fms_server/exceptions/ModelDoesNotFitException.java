/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 1:48 PM
 */

package fms_server.exceptions;

public class ModelDoesNotFitException extends Exception {
    public ModelDoesNotFitException() {}

    public ModelDoesNotFitException(String message) {
        super(message);
    }
}
