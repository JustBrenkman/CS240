package fms_server;

import fms_server.dao.DataBase;
import fms_server.dao.DataBaseException;
import fms_server.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class DataBaseTest {
    @Test
    public void createTables() {
        Logger.info("Normal");
        Logger.warn("Warn");
        Logger.error("Error");
        Logger.pass("Pass");
        Logger.fail("Fail");
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
