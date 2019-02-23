package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.PersonDAO;
import fms_server.logging.Logger;
import fms_server.models.Person;
import fms_server.requests.AuthenticatedRequest;
import fms_server.results.PersonResult;
import fms_server.results.PersonsResult;
import fms_server.services.NotAuthenticatedException;
import fms_server.services.PersonService;

import java.io.IOException;

public class PersonsHandler extends Handler {
    PersonService service;
    public PersonsHandler(String url) {
        super(url);
        service = new PersonService(new PersonDAO());
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            PersonsResult result = service.getAllPersons(new AuthenticatedRequest(exchange.getRequestHeaders().get("auth_token").get(0)));
            String json = gson.toJson(result);
            exchange.sendResponseHeaders(result.isSuccess()? 200: 202, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        } catch (NotAuthenticatedException | IOException e) {
            Logger.warn("Not authenticated", e);
            exchange.sendResponseHeaders(401, 0);
        }
        exchange.close();
    }
}
