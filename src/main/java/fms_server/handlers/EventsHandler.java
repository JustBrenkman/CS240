package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.EventDAO;
import fms_server.logging.Logger;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.EventRequest;
import fms_server.results.EventResult;
import fms_server.results.EventsResult;
import fms_server.services.EventService;
import fms_server.services.NotAuthenticatedException;

import java.io.IOException;

public class EventsHandler extends Handler {
    private EventService service;
    public EventsHandler() {
        service = new EventService(new EventDAO());
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            String[] args = exchange.getRequestURI().getPath().split("/");
            if (args.length > 2) {
                EventRequest request = new EventRequest(getAuthToken(exchange.getRequestHeaders()), args[2]);
                EventResult result = service.getEvent(request);
                String json = gson.toJson(result);
                exchange.sendResponseHeaders(result.isSuccess()? 200: 202, json.getBytes().length);
                exchange.getResponseBody().write(json.getBytes());
            } else {
                EventsResult result = service.getEventList(new AuthenticatedRequest(getAuthToken(exchange.getRequestHeaders())));
                String json = gson.toJson(result);
                exchange.sendResponseHeaders(result.isSuccess()? 200: 202, json.getBytes().length);
                exchange.getResponseBody().write(json.getBytes());
            }
        } catch (NotAuthenticatedException e) {
            Logger.warn("Not authenticated", e);
            String result = gson.toJson(new EventsResult(false, "Not authorized", null));
            exchange.sendResponseHeaders(401, result.getBytes().length);
            exchange.getResponseBody().write(result.getBytes());
        } finally {
            exchange.close();
        }
    }
}
