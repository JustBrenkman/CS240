package fms_server;

import fms_server.dao.DataBase;
import fms_server.dao.DataBaseException;
import fms_server.dao.EventDAO;
import fms_server.dao.ModelNotFoundException;
import fms_server.logging.Logger;
import fms_server.models.Event;
import org.junit.jupiter.api.*;

public class EventDAOTest {
    @BeforeAll
    static void setup() throws DataBaseException {
        Logger.setUpLogSaver();
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.head("Testing EventDAO test cases");
        DataBase.createTables();
    }

    /**
     * This will make sure that each run is on an empty table
     */
    @BeforeEach
    void setUpTable() {
        EventDAO eventDAO = new EventDAO();
        try {
            eventDAO.clear();
        } catch (DataBaseException e) {
            e.printStackTrace();
            Logger.fail("Set up tables");
            Assertions.fail();
        }
//        Logger.pass("Set up tables");
    }

    /**
     * Test adding a bunch of stiff to the table
     */
    @Test
    @DisplayName("ADD pass test case")
    void addPass() {
        EventDAO eventDAO = new EventDAO();
        try {
            Event eventToAdd = new Event("3", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            Event eventToAdd1 = new Event("4", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            Event eventToAdd2 = new Event("5", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            eventDAO.add(eventToAdd);
            eventDAO.add(eventToAdd1);
            eventDAO.add(eventToAdd2);
        } catch (DataBaseException e) {
            e.printStackTrace();
            Logger.fail("Adding to Event table test case");
            Assertions.fail();
        }
        Logger.pass("Passed adding test case");
    }

    /**
     * Tests adding two identical objects
     */
    @Test
    @DisplayName("ADD fail test case")
    void addFail() {
        Assertions.assertThrows(DataBaseException.class, () -> {
            EventDAO eventDAO = new EventDAO();
            Event eventToAdd = new Event("3", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            eventDAO.add(eventToAdd);
            eventDAO.add(eventToAdd); // This throws the error
        });
        Logger.pass("Passed adding two identical events");
    }

    /**
     * Tests DAO to get several events from database
     */
    @Test
    @DisplayName("GET pass test case")
    void getPass() {
        EventDAO eventDAO = new EventDAO();
        try {
            Event eventToAdd = new Event("3", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            Event eventToAdd1 = new Event("4", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            Event eventToAdd2 = new Event("5", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            eventDAO.add(eventToAdd);
            eventDAO.add(eventToAdd1);
            eventDAO.add(eventToAdd2);
            Event eventTest = eventDAO.get("3");
            Assertions.assertEquals(eventTest, eventToAdd);
            eventTest = eventDAO.get("4");
            Assertions.assertEquals(eventTest, eventToAdd1);
            eventTest = eventDAO.get("5");
            Assertions.assertEquals(eventTest, eventToAdd2);
        } catch (DataBaseException | ModelNotFoundException e) {
            Assertions.fail();
        }
        Logger.pass("Passed get event test case");
    }


    /**
     * Gets an id that doesn't exist, must fail
     */
    @Test
    @DisplayName("GET fail test case")
    void getFail() {
        Assertions.assertThrows(ModelNotFoundException.class, () -> {
           EventDAO eventDAO = new EventDAO();
           eventDAO.get("1");
        });
        Logger.pass("Passed get non existent event test case");
    }

    /**
     * Adds an event then deletes it
     */
    @Test
    @DisplayName("DELETE pass test case")
    void deletePass() {
        try {
            EventDAO eventDAO = new EventDAO();
            Event eventToAdd = new Event("1", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019);
            eventDAO.add(eventToAdd);
            eventDAO.delete("1");
        } catch (DataBaseException | ModelNotFoundException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        Logger.pass("Passed deletion of event");
    }

    /**
     * Tries to delete an event that does not exist
     */
    @Test
    @DisplayName("DELETE fail test case")
    void deleteFail() {
        Assertions.assertThrows(ModelNotFoundException.class, () -> {
            EventDAO eventDAO = new EventDAO();
            eventDAO.delete("1");
        });
        Logger.pass("Passed deletion of event that does not exist");
    }

    /**
     * Adds a couple events to database, clears then checks to see if they are still in the database
     */
    @Test
    @DisplayName("DELETE all test case")
    void deleteAll() {
        Assertions.assertAll(() -> {
            try {
                EventDAO eventDAO = new EventDAO();
                eventDAO.add(new Event("1", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019));
                eventDAO.add(new Event("3", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019));
                eventDAO.clear();
                Assertions.assertThrows(ModelNotFoundException.class, () -> eventDAO.get("1"));
                Assertions.assertThrows(ModelNotFoundException.class, () -> eventDAO.get("3"));
            } catch (DataBaseException e) {
                Logger.fail("Failed to delete all entries");
                Assertions.fail();
            }
            Logger.pass("Passed deletion of all entries");
        });
    }

    @AfterAll
    public static void end() {
        Logger.line();
        Logger.flush();
    }
}
