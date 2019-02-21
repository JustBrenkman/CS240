package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fms_server.logging.Logger;

public abstract class Handler implements HttpHandler {
    public String getLogServerCall(String handler, HttpExchange exchange, int returnVal) {
        return exchange.getRemoteAddress().getHostName() + " -- \"" + exchange.getRequestMethod() + "\" /user/register " + exchange.getProtocol() + " " + returnVal;
    }
}
