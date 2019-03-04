/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.results;

/**
 * Clear result class used to respond to an HttpExchange
 */
public class ClearResult extends Result {

    /**
     * Constructor for result class
     *
     * @param success whether or not request was successful
     * @param message message about result
     */
    public ClearResult(boolean success, String message) {
        super(success, message);
    }
}
