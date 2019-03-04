/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.handlers;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
