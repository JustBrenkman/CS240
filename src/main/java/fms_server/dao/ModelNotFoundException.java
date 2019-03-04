/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.dao;

public class ModelNotFoundException extends Exception {
    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException() {
    }
}
