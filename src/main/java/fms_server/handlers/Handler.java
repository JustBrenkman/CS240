package fms_server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fms_server.logging.Logger;

import java.io.IOException;
import java.io.InputStream;

public abstract class Handler implements HttpHandler {
    /**
     * Gson instance to convert json string into objects
     */
    Gson gson;
    /**
     * URL of the handler
     */
    private String url;

    public Handler(String url) {
        gson = new Gson();
        this.url = url;
    }

    /**
     * Formats a string to display useful information
     * @param handler handler extension ural
     * @param exchange HttpExchange that contains information
     * @param returnVal response code of the exchange
     * @return formatted string
     */
    private String getServerCallLog(String handler, HttpExchange exchange, int returnVal) {
        return " -- " + exchange.getRemoteAddress().getHostName() + " -- \"" + exchange.getRequestMethod() + "\" " + url + " " + exchange.getProtocol() + " " + returnVal;
    }

    /**
     * Converts an input stream into a request object. Note inputstream must contain a json formatted string
     * @param inputStream Data from exchange
     * @param tClass Class to convert into
     * @param <T> Type of class
     * @return instance of request
     * @throws IOException if something goes wrong
     */
    <T> T convertToRequest(InputStream inputStream, Class<T> tClass) throws BadRequestException {
        T instance = null;
        try {
            instance =  gson.fromJson(byteArrayToJSONString(inputStream.readAllBytes()), tClass);
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
        handleRequest(exchange);
        Logger.info(getServerCallLog(url, exchange, exchange.getResponseCode()));
    }

    /**
     * This is the handler callback when a url is triggered. Will create a new user if one does not exist
     * @param exchange Data about the request
     * @throws IOException if we are unable to login through exception
     */
    public abstract void handleRequest(HttpExchange exchange) throws IOException;
}
