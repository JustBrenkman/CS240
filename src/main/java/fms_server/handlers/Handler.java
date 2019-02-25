package fms_server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fms_server.logging.Logger;
import fms_server.requests.Request;
import fms_server.services.NotAuthenticatedException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Handler implements HttpHandler {
    /**
     * Gson instance to convert json string into objects
     */
    Gson gson;

    public Handler() {
        gson = new Gson();
    }

    /**
     * Formats a string to display useful information
     * @param handler handler extension ural
     * @param exchange HttpExchange that contains information
     * @param returnVal response code of the exchange
     * @return formatted string
     */
    private String getServerCallLog(String handler, HttpExchange exchange, int returnVal) {
        return " -- " + exchange.getRemoteAddress().getHostName() + " -- \"" + exchange.getRequestMethod() + "\" " + exchange.getRequestURI().getPath() + " " + exchange.getProtocol() + " " + returnVal;
    }

    /**
     * Converts an input stream into a request object. Note inputstream must contain a json formatted string
     * @param inputStream Data from exchange
     * @param tClass Class to convert into
     * @param <T> Type of class
     * @return instance of request
     * @throws IOException if something goes wrong
     */
    <T extends Request> T convertToRequest(InputStream inputStream, Class<T> tClass) throws BadRequestException {
        T instance = null;
        try {
            instance = gson.fromJson(byteArrayToJSONString(inputStream.readAllBytes()), tClass);
            if (instance == null)
                throw new BadRequestException("Unable to create request instance");
            instance.checkForProperInstantiation();
        } catch (IOException e) {
            throw new BadRequestException("Unable to read data from input stream");
        }

        if (instance == null) {
            throw new BadRequestException("Didn't have the required information to convert into request");
        }

        return instance;
    }

    /**
     * Turns an array of bytes to String
     * @param data byte array
     * @return String that should be a json format
     */
    private String byteArrayToJSONString(byte[] data) {
        return new String(data);
    }

    /**
     * This is the http exchange handler callback. We will do some stuff with the exchange before calling the
     * handler callback, the handler will then be able to process the request
     * @param exchange Data about the request
     * @throws IOException if we are unable to login through exception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            handleRequest(exchange);
        } catch (Exception e) {
            Logger.error("Something went wrong during the handle", e);
            if (e instanceof IOException) // Throw the exception, the server might still expect it
                throw e;
        } finally {
            Logger.head(getServerCallLog(exchange.getRequestURI().getPath(), exchange, exchange.getResponseCode()));
        }
    }

    /**
     * This is the handler callback when a url is triggered. Will create a new user if one does not exist
     * @param exchange Data about the request
     * @throws IOException if we are unable to login through exception
     */
    public abstract void handleRequest(HttpExchange exchange) throws IOException;

    public String getAuthToken(Headers headers) throws NotAuthenticatedException {
        Logger.fine(Arrays.toString(headers.entrySet().toArray()));
        Set<Map.Entry<String, List<String>>> head = headers.entrySet();
        for (Map.Entry<String, List<String>> entry : head) {
            if (entry.getKey().toLowerCase().equals("authorization") || entry.getKey().toLowerCase().equals("auth_token")) {
                if (!entry.getValue().isEmpty()) {
                    Logger.info("auth_token: " + entry.getValue().get(0));
                    return entry.getValue().get(0);
                }
            }
        }
        throw new NotAuthenticatedException("There is no auth_token");
    }
}
