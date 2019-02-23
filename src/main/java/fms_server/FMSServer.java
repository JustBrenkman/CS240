package fms_server;

import com.sun.net.httpserver.HttpServer;
import fms_server.handlers.ClearHandler;
import fms_server.handlers.LoginHandler;
import fms_server.handlers.PersonsHandler;
import fms_server.handlers.RegisterHandler;
import fms_server.logging.LogSaver;
import fms_server.logging.Logger;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.net.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FMSServer {
    private int port;
    private InetSocketAddress inetSocketAddress;
    private final String registerURL = "/user/register";
    private final String clearURL = "/clear";
    private final String loginURL = "/user/login";
    private final String personsURL = "/person";
    private final String eventsURL = "/event";
    private static final Key key;

    static {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static Key getKey() {
        return key;
    }

    public static void main(String...args) {
        FMSServer server = new FMSServer();
        server.setupLogging();
        try {
            server.startServer(4201);
            Logger.head("Started server listening on http://localhost:" + server.getPort() + "/");
            StringBuilder str = new StringBuilder();
            for (String add : server.getMachineAddresses())
                str.append(", http://").append(add).append(":").append(server.getPort()).append("/");
            Logger.head("Running on machine at these addresses" + str.toString());
            Logger.warn("Server is running in production mode, this could result in unnecessary changes to the database");
        } catch (IOException e) {
            Logger.severe("Unable to start server, check the log file to see what went wrong", e);
        } finally {
            Logger.flush();
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
        Logger.info("Creating handlers");
        server.createContext(registerURL, new RegisterHandler(registerURL));
        server.createContext(clearURL, new ClearHandler(clearURL));
        server.createContext(loginURL, new LoginHandler(loginURL));
        server.createContext(personsURL, new PersonsHandler(personsURL));
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        if (inetSocketAddress == null)
            return null;
        return inetSocketAddress.getHostName();
    }

    public List<String> getMachineAddresses() throws SocketException {
        List<String> addresses = new ArrayList<>();
        for (
                final Enumeration< NetworkInterface > interfaces =
                NetworkInterface.getNetworkInterfaces( );
                interfaces.hasMoreElements( );
        ) {
            final NetworkInterface cur = interfaces.nextElement();
            if (cur.isLoopback()) {
                continue;
            }
            for (final InterfaceAddress addr : cur.getInterfaceAddresses()) {
                final InetAddress inet_addr = addr.getAddress();

                if (!(inet_addr instanceof Inet4Address)) {
                    continue;
                }
                addresses.add(inet_addr.getHostAddress());
            }
        }
        return addresses;
    }

    private void setupLogging() {
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.setLogClass(true);
        Logger.setLogSaver(new LogSaver("logs"));
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public String getRegisterURL() {
        return registerURL;
    }
}
