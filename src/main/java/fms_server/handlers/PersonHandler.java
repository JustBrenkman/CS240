package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PersonHandler extends Handler {
    public PersonHandler(String url) {
        super(url);
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {

    }
}
