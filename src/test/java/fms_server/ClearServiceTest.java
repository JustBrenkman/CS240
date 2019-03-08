/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:20 PM
 */

package fms_server;

import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.services.ClearService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClearServiceTest {
    ClearService clearService;

    public ClearServiceTest() {
        clearService = new ClearService(new EventDAO(), new PersonDAO(), new UserDAO());
    }

    @Test
    @DisplayName("Clear PASS")
    void clearPass() {
        Assertions.assertTrue(clearService.clear(null).isSuccess());
    }
}
