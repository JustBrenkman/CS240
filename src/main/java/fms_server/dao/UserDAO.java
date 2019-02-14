package fms_server.dao;

import fms_server.models.User;

import java.util.List;
import java.util.Map;

/**
 * User Data Access Object for fetching, modifying and adding new objects of User type
 */
public class UserDAO implements DatabaseAccessObject<User, Integer> {
    /**
     * Table name in the database for users
     */
    public String tableName;

    /**
     * This is the UserDAO public constrictor, sets the table name of the database
     */
    public UserDAO() {}

    /**
     * Gets a user object with id
     * @param id Identifier of object
     * @return user object
     */
    @Override
    public User get(Integer id) {
        return null;
    }

    /**
     * Gets a list of all users in the database
     * @return list of all users in database
     */
    @Override
    public List<User> getAll() {
        return null;
    }

    /**
     * Adds new user to database
     * @param user user object to add
     */
    @Override
    public void add(User user) {

    }

    /**
     * Updates user object in database
     * @param user user object to update
     */
    @Override
    public void update(User user) {

    }

    /**
     * Checks to see if object exists in database
     * @param id identifier of the object
     * @return whether or not the object exists
     */
    @Override
    public boolean doesExist(Integer id) {
        return false;
    }

    /**
     * Removes a user object from the database
     * @param id identifier of the object
     */
    @Override
    public void drop(Integer id) {

    }

    /**
     * Filter user objects
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return list of filtered objects
     */
    @Override
    public List<User> filter(Map<String, Object> queries) {
        return null;
    }

    /**
     * Gets a user based on email to check
     * @param email email of user
     * @return user object with email as above
     */
    public User getUserByEmail(String email) {
        return null;
    }
}
