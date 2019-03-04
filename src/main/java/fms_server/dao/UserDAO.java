/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.dao;

import fms_server.annotation.Unimplemented;
import fms_server.logging.Logger;
import fms_server.models.AbstractModel;
import fms_server.models.ModelDoesNotFitException;
import fms_server.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User Data Access Object for fetching, modifying and adding new objects of User type
 */
public class UserDAO implements IDatabaseAccessObject<User, String> {
    /**
     * Table name in the database for users
     */
    public String tableName;

    /**
     * This is the UserDAO public constrictor, sets the table name of the database
     */
    public UserDAO() {super();}

    @Override
    public void addAll(List<User> list) throws DataBaseException {

    }

    /**
     * Gets a user object with id
     * @param id Identifier of object
     * @return user object
     */
    @Override
    public User get(String id) throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM users WHERE id=?";
        User user = null;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = AbstractModel.castToModel(User.class, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to get user, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (user == null)
            throw new ModelNotFoundException("Could not find user, likely wrong id");

        return user;
    }

    /**
     * Gets a user based on email to check
     * @param username email of user
     * @return user object with email as above
     */
    public User getUserByUsername(String username) throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM users WHERE username=?";
        User user = null;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = AbstractModel.castToModel(User.class, rs);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            throw new DataBaseException("Failed to get user, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
//            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (user == null)
            throw new ModelNotFoundException("Could not find user, likely wrong id");

        return user;
    }

    /**
     * Gets a list of all users in the database
     * @return list of all users in database
     */
    @Override
    public List<User> getAll() throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        Connection connection = DataBase.getConnection(false);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(AbstractModel.castToModel(User.class, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to get user, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (users.isEmpty())
            throw new ModelNotFoundException("Could not find user, likely wrong id");

        return users;
    }

    /**
     * Adds new user to database
     * @param user user object to add
     */
    @Override
    public void add(User user) throws DataBaseException {
        boolean commit = false;
        String sql = "INSERT INTO users (id, username, email, password, firstName, lastName, gender)" +
                "VALUES (?,?,?,?,?,?,?)";
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setObject(7, user.getGender());

            stmt.executeUpdate();
            commit = true;
            Logger.fine("Added: " + user.toString());
        } catch (SQLException e) {
//            e.printStackTrace();
            Logger.warn("Failed to add user object, check password or could be identical", e);
            throw new DataBaseException("Unable to perform query, double check your password configuration");
        } finally {
            DataBase.closeConnection(commit);
        }
    }

    /**
     * Updates user object in database
     * @param user user object to update
     */
    @Override
    @Unimplemented
    public void update(User user) {

    }

    /**
     * Checks to see if object exists in database
     * @param id identifier of the object
     * @return whether or not the object exists
     */
    @Override
    public boolean doesExist(String id) {
        return false;
    }

    /**
     * Removes a user object from the database
     * @param id identifier of the object
     */
    @Override
    public void delete(String id) throws DataBaseException, ModelNotFoundException {
        String sql = "DELETE FROM users WHERE id=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            commit = stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataBaseException("Unable remove entry");
        } finally {
            DataBase.closeConnection(commit);
        }
        if (!commit)
            throw new ModelNotFoundException("SQL query did not delete anything");
    }

    /**
     * Clears all events
     */
    @Override
    public void clear() throws DataBaseException {
        String sql = "DELETE FROM users";
        Connection connection = DataBase.getConnection(false);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Unable to truncate table");
        } finally {
            DataBase.closeConnection(true);
        }
    }

    public void deleteAll(String des) throws DataBaseException, ModelNotFoundException {
        String sql = "DELETE FROM users WHERE descendant=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, des);
            commit = stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Unable remove entry");
        } finally {
            DataBase.closeConnection(commit);
        }
        if (!commit)
            throw new ModelNotFoundException("SQL query did not delete anything");
    }

    /**
     * Filter user objects
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return list of filtered objects
     */
    @Override
    @Unimplemented
    public List<User> filter(Map<String, Object> queries) {
        return null;
    }
}
