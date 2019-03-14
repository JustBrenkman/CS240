/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/14/19 4:33 PM
 */

package fms_server.handlers;

import com.google.common.annotations.Beta;
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
    @Beta
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            String response = gson.toJson(service.call(ClearResult.class, null));
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
        } catch (NoSuchMethodException e) {
            Logger.severe("Unable to clear tables", e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        }
    }
}
