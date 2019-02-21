package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.logging.Logger;

import java.io.IOException;

/**
 * This is the class used to handle registration events, will access the UserDAO and create a new user
 */
public class RegisterHandler implements Handler {
    /**
     * This is the handler callback when a url is triggered. Will create a new user if one does not exist
     * @param exchange Data about the request
     * @throws IOException if we are unable to login through exception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Logger.info(RegisterHandler.class, "/user/register");
        exchange.getResponseBody().write(202);
        exchange.close();
    }
}
