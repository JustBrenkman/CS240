package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.EventDAO;
import fms_server.logging.Logger;
import fms_server.services.EventService;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

public class EventsHandler extends Handler {
    private EventService service;
    public EventsHandler() {
        service = new EventService(new EventDAO());
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        Logger.info(exchange.getRequestURI().getPath());
        String[] args = exchange.getRequestURI().getPath().split("/");
        if (args.length > 1) {
            // Get the event with id
            String eventID = args[1]
;        } else {
            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
        }
        exchange.close();
    }
}
