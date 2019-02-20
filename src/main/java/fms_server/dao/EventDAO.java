package fms_server.dao;

import fms_server.models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void add(Event event) {
        String sql = "INSERT INTO Events (EventID, Descendant, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
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
    public void clear() {

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
