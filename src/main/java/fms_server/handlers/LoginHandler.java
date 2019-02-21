package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * This is the login handler class, it will handle the login url extension
 */
public class LoginHandler extends Handler {
    /**
     * Will handle the login request, checks to see if user exists and also checks to see if passwords match
     * @param exchange Http request
     * @throws IOException if something goes bad through an exception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
