package fms_server;

import fms_server.dao.DataBase;
import fms_server.dao.DataBaseException;
import fms_server.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class DataBaseTest {
    @BeforeAll
    public static void testLogger() {
        Logger.head("Testing Database");
    }

    @AfterAll
    public static void end() {
        Logger.line();
    }

    @Test
    public void createTables() {
        Logger.info("Creating tables");
        try {
            DataBase.createTables();
        } catch (DataBaseException e) {
            e.printStackTrace();
            Assertions.fail("Something went wrong trying to create the databases");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Logger.pass("Passed create tables test");
    }
}
