package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.requests.LoginRequest;
import fms_server.results.LoginResult;
import fms_server.services.LoginService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * This is the login handler class, it will handle the login url extension
 */
public class LoginHandler extends Handler {
    LoginService service;

    public LoginHandler() {
        service = new LoginService(new UserDAO());
    }

    /**
     * Logs in a user using their email and password. Returns an auth token
     * @param exchange Data about the request
     * @throws IOException something went wrong
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            LoginResult result = service.login(convertToRequest(exchange.getRequestBody(), LoginRequest.class));
            String json = gson.toJson(result);
//            Logger.info("Returning: " + json);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        } catch (BadRequestException e) {
            Logger.error("Bad request, couldn't complete request", e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        } finally {
            exchange.close();
        }
    }
}
