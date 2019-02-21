package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Handles the clear extension
 */
public class ClearHandler extends Handler {
    /**
     * This function will handle the /clear path. Will clear all information in the databases
     * @param exchange The HttpExchange information
     * @throws IOException through an exception if something went wrong
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
