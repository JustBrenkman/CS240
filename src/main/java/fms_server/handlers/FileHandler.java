/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler extends Handler {
    private String webPath = "res/web";

    /**
     * Get files that are requested
     *
     * @param exchange Data about the request
     * @throws IOException if file cannot be found
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path == null || path.equals("/"))
            path = "/index.html";

        path = webPath + path;
        try {
            // Check to see if the file exists
            File file = new File(path);
            if (!file.exists())
                throw new FileNotFoundException();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, Files.size(Paths.get(path)));
            Files.copy(Paths.get(path), exchange.getResponseBody());
        } catch (FileNotFoundException e) {
            Logger.error("Unable to find file", e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        } finally {
            exchange.close();
        }
    }
}
