/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/16/19 5:07 PM
 */

package fms_server.requests;

public class FillRequest extends Request {
    /**
     * userName of the fill request
     */
    private final String userName;
    /**
     * Number of generations to fill
     */
    private final int generations;

    /**
     * Fill request constructor
     * @param userName userName of the person to fill
     * @param generations number of generations to fill
     */
    public FillRequest(String userName, int generations) {
        this.userName = userName;
        this.generations = generations;
    }

    public String getuserName() {
        return userName;
    }

    public int getGenerations() {
        return generations;
    }
}
