package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.results.ClearResult;
import fms_server.services.ClearService;

import java.io.IOException;

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
        ClearResult clearResult = service.clear();
        String response = gson.toJson(clearResult);
        exchange.sendResponseHeaders(clearResult.isSuccess()? 200: 202, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
