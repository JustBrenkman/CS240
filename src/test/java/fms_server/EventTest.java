package fms_server;

import fms_server.dao.DataBase;
import fms_server.dao.DataBaseException;
import fms_server.dao.EventDAO;
import fms_server.logging.Logger;
import fms_server.models.Event;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @BeforeAll
    static void setup() throws DataBaseException {
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.head("Testing EventDAO test cases");
        DataBase.createTables();
    }

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
    void addPass() {
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
        } catch (DataBaseException e) {
            e.printStackTrace();
            Logger.fail("Adding to Event table test case");
            Assertions.fail();
        }
        Logger.pass("Passed adding test case");
    }

    @Test
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
        } catch (DataBaseException e) {

            Assertions.fail();
        }
    }

    @AfterAll
    public static void end() {
        Logger.line();
    }
}
