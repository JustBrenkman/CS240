package fms_server;

import com.sun.net.httpserver.HttpServer;
import fms_server.handlers.RegisterHandler;
import fms_server.logging.LogSaver;
import fms_server.logging.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FMSServer {
    private int port;
    private InetSocketAddress inetSocketAddress;

    public static void main(String...args) {
        FMSServer server = new FMSServer();
        server.setupLogging();
        try {
            server.startServer();
            Logger.head("Started server listening on http://localhost:" + server.getPort() + "/");
        } catch (IOException e) {
            Logger.severe("Unable to start server, check the log file to see what went wrong", e);
        }
    }

    private void startServer(int port) throws IOException {
        inetSocketAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(inetSocketAddress, 10);
        registerHandlers(server);
        server.start();
        this.port = port;
    }

    private void startServer() throws IOException {
        startServer(80);
    }

    private void registerHandlers(HttpServer server) {
        server.createContext("/user/register", new RegisterHandler());
        Logger.info("Added register handler");
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        if (inetSocketAddress == null)
            return null;
        return inetSocketAddress.getHostName();
    }

    private void setupLogging() {
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.setLogClass(true);
        Logger.setLogSaver(new LogSaver("logs"));
    }
}
