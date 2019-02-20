package fms_server.dao;

import fms_server.models.Event;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Event Database Access Object
 */
public class EventDAO implements IDatabaseAccessObject<Event, String> {
    /**
     * Gets an event object from database
     * @param id Identifier of object
     * @return Event object
     */
    @Override
    public Event get(String id) throws DataBaseException {
        String sql = "SELECT * WHERE eventID=?";
        Event event = null;
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("descendant"), rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getString("country"), rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
            }
            DataBase.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Failed to do stuff");
        }
        return event;
    }

    /**
     * Gets all event objects in database
     * @return List of Event's
     */
    @Override
    public List<Event> getAll() {
        return null;
    }

    /**
     * Adds new Event object to database
     * @param event Event object to add
     */
    @Override
    public void add(Event event) throws DataBaseException {
        String sql = "INSERT INTO events (eventID, descendant, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection connection = DataBase.getConnection(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException("Error encountered while inserting into the database");
        } finally {
            DataBase.closeConnection(true);
        }
    }

    /**
     * Update an event object
     * @param event event object to add
     */
    @Override
    public void update(Event event) {

    }

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
    public void drop(String id) {

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
    public List<Event> filter(Map<String, Object> queries) {
        return null;
    }
}
