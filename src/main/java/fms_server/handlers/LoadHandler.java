package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.requests.LoadRequest;
import fms_server.results.LoadResult;
import fms_server.services.LoadService;

import java.io.IOException;

public class LoadHandler extends Handler {
    LoadService service = new LoadService(new EventDAO(), new UserDAO(), new PersonDAO());

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        try {
            LoadResult result = service.load(convertToRequest(exchange.getRequestBody(), LoadRequest.class));
            String json = gson.toJson(result);
            exchange.sendResponseHeaders(result.isSuccess() ? 200 : 202, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        } catch (BadRequestException e) {
            Logger.error("Something is wrong with the request", e);
            exchange.sendResponseHeaders(400, 0);
        } finally {
            exchange.close();
        }
    }
}
