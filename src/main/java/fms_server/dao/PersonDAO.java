package fms_server.dao;

import fms_server.models.Person;
import fms_server.models.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Person Database Access Object
 */
public class PersonDAO implements IDatabaseAccessObject<Person, Integer> {
    /**
     * Get's person object from database
     * @param id Identifier of object
     * @return person object
     */
    @Override
    public Person get(Integer id) throws DataBaseException {
        return null;
    }

    /**
     * Gets all person objects in database
     * @return List of person objects
     */
    @Override
    public List<Person> getAll() {
        return null;
    }

    /**
     * Add new person object to database
     * @param person person object
     */
    @Override
    public void add(Person person) {

    }

    /**
     * Update person object in database
     * @param person object to update
     */
    @Override
    public void update(Person person) {

    }

    /**
     * Checks to see if person exists
     * @param id identifier of the object
     * @return boolean
     */
    @Override
    public boolean doesExist(Integer id) {
        return false;
    }

    /**
     * Deletes a person
     * @param id identifier of the object
     */
    @Override
    public void delete(Integer id) {

    }

    /**
     * Clears all persons
     */
    @Override
    public void clear() {

    }

    /**
     * Filters stuff
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return List
     */
    @Override
    public List<Person> filter(Map<String, Object> queries) {
        return null;
    }

    /**
     * Gets a list of persons that are associated with the user
     * @param user user that is in question
     * @return List of person objects
     */
    public List<Person> filterByUser(User user) {return null;}
}
