/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.dao;

import fms_server.annotation.Unimplemented;
import fms_server.exceptions.DataBaseException;

import java.util.List;
import java.util.Map;

/**
 * This is the Data Access Object interface, gives a simple interface to be used with all IDatabaseAccessObject's
 * @param <T> This is the type of the IDatabaseAccessObject
 * @param <V> This is the type of the identifier, can be Integer or String
 */
public interface IDatabaseAccessObject<T, V> {
    /**
     * Adds a list of items one by one
     * @param list list of items to add
     * @throws DataBaseException if something happens it will stop and not commit
     */
    void addAll(List<T> list) throws DataBaseException;

    /**
     * This gets the object based on the id, assuming all objects contain an id
     * @param id Identifier of object
     * @return Object that has been requested
     */
    T get(V id) throws DataBaseException, ModelNotFoundException;

    /**
     * Returns all objects in the database
     * @return a list of all the objects in the database
     */
    List<T> getAll() throws DataBaseException, ModelNotFoundException;

    /**
     * Adds a new object to the database
     * @param t object to add
     */
    void add(T t) throws DataBaseException;

    /**
     * Updates the object using the id of the object with the rest of the variables, effectively replacing all variables
     * @param t Object to update
     */
    void update(T t) throws ModelNotFoundException, DataBaseException;

    /**
     * Checks to see if object with that id exists
     * @param id identifier of the object
     * @return whether or not object exists or not
     */
    boolean doesExist(V id);

    /**
     * Drops an object
     * @param id identifier of the object
     */
    void delete(V id) throws DataBaseException, ModelNotFoundException;

    /**
     * Truncates database
     */
    void clear() throws DataBaseException;

    /**
     * Returns a list where the query is filter with the map
     * Map keys are the var name to filter and values are the values that the keys are filtered by
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return List of DAO objects
     */
    @Unimplemented
    List<T> filter(Map<String, Object> queries);
}
