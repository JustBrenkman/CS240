/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.results.ClearResult;
import fms_server.services.ClearService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Handles the clear extension
 */
public class ClearHandler extends Handler {
    private ClearService service;

    public ClearHandler() {
        service = new ClearService(new UserDAO(), new EventDAO(), new PersonDAO());
    }

    /**
     * This function will handle the /clear path. Will clear all information in the databases
     * @param exchange The HttpExchange information
     * @throws IOException through an exception if something went wrong
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            ClearResult clearResult = service.call(ClearResult.class, null);
            String response = gson.toJson(clearResult);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
        } catch (NoSuchMethodException e) {
            Logger.severe("Unable to clear tables", e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        } finally {
            exchange.close();
        }
    }
}
