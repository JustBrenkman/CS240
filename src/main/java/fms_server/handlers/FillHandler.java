package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * /fill extension handler
 */
public class FillHandler extends Handler {
    /**
     * Handles the httpexchange information to fill the database with generated information
     * @param exchange HttpExchange that has information about the request
     * @throws IOException throughs an exception if something goes wrong
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
