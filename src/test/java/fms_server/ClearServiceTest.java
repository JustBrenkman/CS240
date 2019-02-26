package fms_server;

import fms_server.dao.DataBase;
import fms_server.dao.DataBaseException;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.results.ClearResult;
import fms_server.services.ClearService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClearServiceTest {
    ClearService service;

    ClearServiceTest() {
        service = new ClearService(new UserDAO(), new PersonDAO(), new UserDAO());
    }

    @BeforeAll
    static void setup() throws DataBaseException {
        Logger.setUpLogSaver();
        Logger.setLogClass(true);
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.head("Testing Clear Service");
        DataBase.createTables();
    }

    @Test
    @DisplayName("Clear database contains data")
    void clearAll() {
        ClearResult result = service.clear();
        if (result.isSuccess())
            Logger.pass("Passed clear all");
        Assertions.assertTrue(result.isSuccess());
    }
}
