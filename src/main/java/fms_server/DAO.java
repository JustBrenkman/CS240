package fms_server;

import java.util.List;

/**
 * This is the Data Access Object interface, gives a simple interface to be used with all DAO's
 * @param <T> This is the type of the DAO
 * @param <V> This is the type of the identifier, can be Integer or String
 */
public interface DAO<T, V> {
    /**
     * This gets the object based on the id, assuming all objects contain an id
     * @param id Identifier of object
     * @return Object that has been requested
     */
    T get(V id);

    /**
     * Returns all objects in the database
     * @return a list of all the objects in the database
     */
    List<T> getAll();

    /**
     * Adds a new object to the database
     * @param t object to add
     */
    void add(T t);

    /**
     * Updates the object using the id of the object with the rest of the variables, effectively replacing all variables
     * @param t Object to update
     */
    void update(T t);

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
    void drop(V id);
}
