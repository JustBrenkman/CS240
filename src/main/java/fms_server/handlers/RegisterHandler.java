package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.requests.RegisterRequest;
import fms_server.results.RegisterResult;
import fms_server.results.Result;
import fms_server.services.RegisterService;

import java.io.IOException;

/**
 * This is the class used to handle registration events, will access the UserDAO and create a new user
 */
public class RegisterHandler extends Handler {
    private RegisterService service;

    public RegisterHandler() {
        service = new RegisterService(new UserDAO(), new PersonDAO(), new EventDAO());
    }

    /**
     * This is the handler callback when a url is triggered. Will create a new user if one does not exist
     * @param exchange Data about the request
     * @throws IOException if we are unable to login through exception
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            Result registerResult = service.register(convertToRequest(exchange.getRequestBody(), RegisterRequest.class));
            String result = gson.toJson(registerResult);
            exchange.sendResponseHeaders(registerResult.isSuccess() ? 200 : 202, result.getBytes().length);
            exchange.getResponseBody().write(result.getBytes());
        } catch (BadRequestException e) {
            Logger.error("Bad request: " + e.getMessage(), e);
            exchange.sendResponseHeaders(400, 0);
        } finally {
            exchange.close();
        }
    }
}
