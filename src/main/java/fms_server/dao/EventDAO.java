/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.dao;

import fms_server.annotation.Unimplemented;
import fms_server.logging.Logger;
import fms_server.models.AbstractModel;
import fms_server.models.Event;
import fms_server.models.ModelDoesNotFitException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Event Database Access Object
 */
public class EventDAO implements IDatabaseAccessObject<Event, String> {
    @Override
    public void addAll(List<Event> list) throws DataBaseException {
        boolean commit = false;
        String sql = "INSERT INTO events (id, descendant, personId, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Event event : list) {
                stmt.setString(1, event.getId());
                stmt.setString(2, event.getDescendant());
                stmt.setString(3, event.getPersonID());
                stmt.setDouble(4, event.getLatitude());
                stmt.setDouble(5, event.getLongitude());
                stmt.setString(6, event.getCountry());
                stmt.setString(7, event.getCity());
                stmt.setString(8, event.getEventType());
                stmt.setInt(9, event.getYear());

                stmt.executeUpdate();
                Logger.fine("Added: " + event.toString());
            }
            Logger.info("Successfully added " + list.size() + " events");
            commit = true;
        } catch (SQLException e) {
//            e.printStackTrace();
            Logger.warn("Unable to add event, could be identical id", e);
            throw new DataBaseException("Error encountered while inserting into the database");
        } finally {
            DataBase.closeConnection(commit);
        }
    }

    /**
     * Gets an event object from database
     * @param id Identifier of object
     * @return Event object
     */
    @Override
    public Event get(String id) throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM events WHERE id=?";
        Event event = null;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                event = AbstractModel.castToModel(Event.class, rs);
                Logger.fine("Added: " + event.toString());

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to get event, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (event == null)
            throw new ModelNotFoundException("Could not find event, likely wrong id");

        return event;
    }

    /**
     * Gets all event objects in database
     * @return List of Event's
     */
    @Override
    public List<Event> getAll() throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM events";
        List<Event> events = new ArrayList<>();
        Connection connection = DataBase.getConnection(false);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                events.add(AbstractModel.castToModel(Event.class, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to get person, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (events.isEmpty())
            throw new ModelNotFoundException("Could not find person, likely wrong id");

        return events;
    }

    /**
     * Gets all event objects in database
     * @return List of Event's
     */
    public List<Event> getAllFromDescendant(String des) throws DataBaseException, ModelNotFoundException {
        String sql = "SELECT * FROM events WHERE descendant=?";
        List<Event> events = new ArrayList<>();
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, des);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(AbstractModel.castToModel(Event.class, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to get person, something is wrong with the SQL command: " + sql);
        } catch (ModelDoesNotFitException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to convert entry to model");
        } finally {
            DataBase.closeConnection(true);
        }

        if (events.isEmpty())
            throw new ModelNotFoundException("Could not find person, likely wrong id");

        return events;
    }

    /**
     * Adds new Event object to database
     * @param event Event object to add
     */
    @Override
    public void add(Event event) throws DataBaseException {
        boolean commit = false;
        String sql = "INSERT INTO events (id, descendant, personId, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getId());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
            commit = true;
        } catch (SQLException e) {
//            e.printStackTrace();
            Logger.warn("Unable to add event, could be identical id", e);
            throw new DataBaseException("Error encountered while inserting into the database");
        } finally {
            DataBase.closeConnection(commit);
        }
    }

    /**
     * Update an event object
     * @param event event object to add
     */
    @Override
    public void update(Event event) {}

    /**
     * Checks to see if an event object exists
     * @param id identifier of the object
     * @return boolean
     */
    @Override
    public boolean doesExist(String id) {
        return false;
    }

    /**
     * Deletes an object from the database
     * @param id identifier of the object
     */
    @Override
    public void delete(String id) throws DataBaseException, ModelNotFoundException {
        String sql = "DELETE FROM events WHERE id=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
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
     * Deletes an object from the database
     * @param des identifier of the object
     */
    public void deleteAll(String des) throws DataBaseException, ModelNotFoundException {
        String sql = "DELETE FROM events WHERE descendant=?";
        boolean commit = false;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, des);
            commit = stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Unable remove entry");
        } finally {
            DataBase.closeConnection(true);
        }
        Logger.info("Deleted: " + commit);
    }

    /**
     * Clears all events
     */
    @Override
    public void clear() throws DataBaseException {
        String sql = "DELETE FROM events";
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

    /**
     * Filters Events
     * @param queries map of keys and values, must have same names as DAO object entries
     * @return List of filtered events
     */
    @Override
    @Unimplemented
    public List<Event> filter(Map<String, Object> queries) {
        return null;
    }
}
