package fms_server.dao;

import fms_server.annotation.Unimplemented;
import fms_server.logging.Logger;
import fms_server.models.AbstractModel;
import fms_server.models.ModelDoesNotFitException;
import fms_server.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Person Database Access Object
 */
public class PersonDAO implements IDatabaseAccessObject<Person, String> {
    @Override
    public void addAll(List<Person> list) throws DataBaseException {
        boolean commit = false;
        String sql = "INSERT INTO persons (id, descendant, firstName, lastName, gender, fatherID, motherID, spouseID)" +
                "VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Person person : list) {
                stmt.setString(1, person.getId());
                stmt.setString(2, person.getDescendant());
                stmt.setString(3, person.getFirstName());
                stmt.setString(4, person.getLastName());
                stmt.setObject(5, person.getGender());
                stmt.setObject(6, person.getFatherID());
                stmt.setObject(7, person.getMotherID());
                stmt.setObject(8, person.getSpouseID());

                stmt.executeUpdate();
                Logger.fine("Added: " + person.toString());
            }
            commit = true;
            Logger.info("Successfully added " + list.size() + " people to the database");
        } catch (SQLException e) {
            Logger.warn("Failed to add person object, check password or could be identical", e);
            throw new DataBaseException("Unable to perform query");
        } finally {
            DataBase.closeConnection(commit);
        }
    }

    /**
     * Get's person object from database
     * @param id Identifier of object
     * @return person object
     */
    @Override
    public Person get(String id) throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM persons WHERE id=?";
        Person person = null;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                person = AbstractModel.castToModel(Person.class, rs);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            throw new DataBaseException("Failed to get person, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
//            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (person == null)
            throw new ModelNotFoundException("Could not find person, likely wrong id");

        return person;
    }

    /**
     * Gets all person objects in database
     * @return List of person objects
     */
    @Override
    public List<Person> getAll() throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM persons";
        List<Person> persons = new ArrayList<>();
        Connection connection = DataBase.getConnection(false);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                persons.add(AbstractModel.castToModel(Person.class, rs));
            }
            Logger.fine("Got: " + persons.size() + " from the database");
        } catch (SQLException e) {
//            e.printStackTrace();
            Logger.error("SQL statement not correct", e);
            throw new DataBaseException("Failed to get person, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
//            e.printStackTrace();
            Logger.warn("Unable to find person", e);
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (persons.isEmpty())
            throw new ModelNotFoundException("Could not find person, likely wrong id");

        return persons;
    }

    /**
     * Add new person object to database
     * @param person person object
     */
    @Override
    public void add(Person person) throws DataBaseException {
        boolean commit = false;
        String sql = "INSERT INTO persons (id, descendant, firstName, lastName, gender, fatherID, motherID, spouseID)" +
                "VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getId());
            stmt.setString(2, person.getDescendant());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setObject(5, person.getGender());
            stmt.setObject(6, person.getFatherID());
            stmt.setObject(7, person.getMotherID());
            stmt.setObject(8, person.getSpouseID());

            stmt.executeUpdate();
            commit = true;
            Logger.fine("Added: " + person.toString());
        } catch (SQLException e) {
//            e.printStackTrace();
            Logger.warn("Failed to add person object, check password or could be identical", e);
            throw new DataBaseException("Unable to perform query");
        } finally {
            DataBase.closeConnection(commit);
        }
    }

    /**
     * Update person object in database
     * @param person object to update
     */
    @Override
    public void update(Person person) throws DataBaseException {
        String sql = "UPDATE persons SET descendant=?, firstname=?, lastName=?, gender=?, fatherID=?, motherID=?, SpouseID=? WHERE id=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getDescendant());
            stmt.setString(2, person.getFirstName());
            stmt.setString(3, person.getLastName());
            stmt.setString(4, person.getGender());
            stmt.setString(5, person.getFatherID());
            stmt.setString(6, person.getMotherID());
            stmt.setString(7, person.getSpouseID());
            stmt.setString(8, person.getId());
            stmt.executeUpdate();
            commit = true;
        } catch (SQLException e) {
            Logger.warn("Unable to update the person information", e);
            throw new DataBaseException("Unable to update person information");
        } finally {
            DataBase.closeConnection(commit);
        }
    }

    /**
     * Checks to see if person exists
     * @param id identifier of the object
     * @return boolean
     */
    @Override
    public boolean doesExist(String id) {
        return false;
    }

    /**
     * Deletes a person
     * @param id identifier of the object
     */
    @Override
    public void delete(String id) throws DataBaseException, ModelNotFoundException {
        String sql = "DELETE FROM persons WHERE id=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            commit = stmt.executeUpdate() == 1;
        } catch (SQLException e) {
//            e.printStackTrace();
            throw new DataBaseException("Unable remove entry");
        } finally {
            DataBase.closeConnection(commit);
        }
        if (!commit)
            throw new ModelNotFoundException("SQL query did not delete anything");
    }

    public void deleteAll(String des, String id) throws DataBaseException, ModelNotFoundException {
        String sql = "DELETE FROM persons WHERE descendant=? AND id!=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, des);
            stmt.setString(2, id);
            commit = stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataBaseException("Unable remove entry");
        } finally {
            DataBase.closeConnection(true);
        }
    }

    /**
     * Clears all persons
     */
    @Override
    public void clear() throws DataBaseException {
        String sql = "DELETE FROM persons";
        Connection connection = DataBase.getConnection(false);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
//            e.printStackTrace();
            throw new DataBaseException("Unable to truncate table");
        } finally {
            DataBase.closeConnection(true);
        }
    }

    /**
     * Filters stuff
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return List
     */
    @Override
    @Unimplemented
    public List<Person> filter(Map<String, Object> queries) {
        return null;
    }
}
