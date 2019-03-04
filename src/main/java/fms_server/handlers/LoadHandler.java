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
import fms_server.requests.LoadRequest;
import fms_server.results.LoadResult;
import fms_server.services.LoadService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {
    LoadService service = new LoadService(new EventDAO(), new UserDAO(), new PersonDAO());

    /**
     * Loads data into database
     *
     * @param exchange Data about the request
     * @throws IOException if something went wrong
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            LoadResult result = service.load(convertToRequest(exchange.getRequestBody(), LoadRequest.class));
            String json = gson.toJson(result);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        } catch (BadRequestException e) {
            Logger.error("Something is wrong with the request", e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        } finally {
            exchange.close();
        }
    }
}
