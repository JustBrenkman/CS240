package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * /fill extension handler
 */
public class FillHandler extends Handler {
    FillHandler(String url) {
        super(url);
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {

    }
}
