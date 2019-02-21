package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.logging.Logger;
import fms_server.results.RegisterResult;

import java.io.IOException;

/**
 * This is the class used to handle registration events, will access the UserDAO and create a new user
 */
public class RegisterHandler extends Handler {
    /**
     * This is the handler callback when a url is triggered. Will create a new user if one does not exist
     * @param exchange Data about the request
     * @throws IOException if we are unable to login through exception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Logger.info(getLogServerCall("/user/register", exchange, 202));
        String response = "Hello";
        exchange.sendResponseHeaders(202, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
