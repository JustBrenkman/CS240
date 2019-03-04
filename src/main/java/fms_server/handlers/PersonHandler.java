/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.ModelNotFoundException;
import fms_server.dao.PersonDAO;
import fms_server.logging.Logger;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.PersonRequest;
import fms_server.results.PersonResult;
import fms_server.results.Result;
import fms_server.services.PersonService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PersonHandler extends Handler {
    private PersonService service;

    public PersonHandler() {
        service = new PersonService(new PersonDAO());
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            String[] args = exchange.getRequestURI().getPath().split("/");
            Result result;

            if (args.length > 2)
                result = service.getPerson(new PersonRequest(getAuthToken(exchange.getRequestHeaders()), args[2]));
            else
                result = service.getAllPersons(new AuthenticatedRequest(getAuthToken(exchange.getRequestHeaders())));

            String json = gson.toJson(result);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());

        } catch (Exception e) {
            Logger.warn(e instanceof ModelNotFoundException ? "User not found" : "Not authenticated", e);
            String result = gson.toJson(new PersonResult(false, e instanceof ModelNotFoundException ?
                    "User not found" : "Not authorized", null));

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, result.getBytes().length);
            exchange.getResponseBody().write(result.getBytes());
        } finally {
            exchange.close();
        }
    }
}
