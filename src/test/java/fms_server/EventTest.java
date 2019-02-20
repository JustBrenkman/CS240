package fms_server;

import fms_server.dao.DataBaseException;
import fms_server.dao.EventDAO;
import fms_server.logging.Logger;
import fms_server.models.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @BeforeAll
    static void testLogger() throws DataBaseException {
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.head("Testing Events");
        Logger.info("Normal");
        Logger.warn("Warn");
        Logger.error("Error");
        Logger.pass("Pass");
        Logger.fail("Fail");
        Logger.line();
    }

    @Test
    void testAdding() {
        EventDAO eventDAO = new EventDAO();
        try {
            eventDAO.clear();
            eventDAO.add(new Event("3", "ben", "1", -111.25, 40.68, "USA", "Provo", "birth", 2019));
        } catch (DataBaseException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @AfterAll
    public static void end() {
        Logger.line();
    }
}
