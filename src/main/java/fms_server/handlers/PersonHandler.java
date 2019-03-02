package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.DataBaseException;
import fms_server.dao.ModelNotFoundException;
import fms_server.dao.PersonDAO;
import fms_server.logging.Logger;
import fms_server.models.Person;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.PersonRequest;
import fms_server.results.PersonResult;
import fms_server.results.PersonsResult;
import fms_server.services.NotAuthenticatedException;
import fms_server.services.PersonService;

import java.io.IOException;

public class PersonHandler extends Handler {
    private PersonService service;

    public PersonHandler() {
        service = new PersonService(new PersonDAO());
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            String[] args = exchange.getRequestURI().getPath().split("/");
            if (args.length > 2) {
                PersonRequest request = new PersonRequest(getAuthToken(exchange.getRequestHeaders()), args[2]);
                PersonResult result = service.getPerson(request);
                String json = gson.toJson(result);
                exchange.sendResponseHeaders(result.isSuccess()? 200: 202, json.getBytes().length);
                exchange.getResponseBody().write(json.getBytes());
            } else {
                PersonsResult result = service.getAllPersons(new AuthenticatedRequest(getAuthToken(exchange.getRequestHeaders())));
                String json = gson.toJson(result);
                exchange.sendResponseHeaders(result.isSuccess()? 200: 202, json.getBytes().length);
                exchange.getResponseBody().write(json.getBytes());
            }
        } catch (Exception e) {
            Logger.warn(e instanceof ModelNotFoundException? "User not found" : "Not authenticated", e);
            String result = gson.toJson(new PersonResult(false, e instanceof ModelNotFoundException? "User not found" : "Not authorized", null));
            exchange.sendResponseHeaders(401, result.getBytes().length);
            exchange.getResponseBody().write(result.getBytes());
        } finally {
            exchange.close();
        }
    }
}
