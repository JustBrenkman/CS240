package fms_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import fms_server.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler extends Handler {
    private String webPath = "res/web";
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path == null || path.equals("/"))
            path = "/index.html";
        path = webPath + path;
        try {
            Logger.fine(path);
            File file = new File(path);
            if (!file.exists())
                throw new FileNotFoundException();
            exchange.sendResponseHeaders(200, Files.size(Paths.get(path)));
            OutputStream out = exchange.getResponseBody();
            Files.copy(Paths.get(path), out);
        } catch (Exception e) {
            Logger.error("Unable to find file", e);
            exchange.sendResponseHeaders(401, 0);
        } finally {
            exchange.close();
        }
    }
}
