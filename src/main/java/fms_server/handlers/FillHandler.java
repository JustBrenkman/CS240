/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.dao.DataBaseException;
import fms_server.dao.EventDAO;
import fms_server.dao.PersonDAO;
import fms_server.dao.UserDAO;
import fms_server.logging.Logger;
import fms_server.requests.FillRequest;
import fms_server.results.FillResult;
import fms_server.services.FillService;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * /fill extension handler
 */
public class FillHandler extends Handler {
    private FillService service;

    public FillHandler() {
        service = new FillService(new EventDAO(), new UserDAO(), new PersonDAO());
    }

    /**
     * Handle the fill handler, generate a bunch of data
     *
     * @param exchange Data about the request
     * @throws IOException if there is an error in fulfilling the request
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        String[] args = exchange.getRequestURI().getPath().split("/");
        Logger.fine("Number or args: " + args.length);
        try {
            if (args.length >= 3) {
                String username = args[2];
                int generations = (args.length >= 4) ? Integer.valueOf(args[3]) : 4;
                Logger.fine("Username: " + username + ", generations: " + generations);
                FillResult result = service.fill(new FillRequest(username, generations));
                String json = gson.toJson(result);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, json.getBytes().length);
                exchange.getResponseBody().write(json.getBytes());
            } else {
                throw new BadRequestException("Not enough params");
            }
        } catch (BadRequestException | NullPointerException | NumberFormatException e) {
            Logger.error("Bad request, check log for more info", e);
            String json = gson.toJson(new FillResult(false, "Bad request"));
            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        } catch (DataBaseException e) {
            Logger.error("Something went wrong generating the data", e);
            String json = gson.toJson(new FillResult(false, "Sever internal error"));
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        } finally {
            exchange.close();
        }
    }
}
