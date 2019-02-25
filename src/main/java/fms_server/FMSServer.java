package fms_server;

import com.sun.net.httpserver.HttpServer;
import fms_server.dao.DataBase;
import fms_server.dao.DataBaseException;
import fms_server.handlers.*;
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
    private HttpServer server;
    private InetSocketAddress inetSocketAddress;
    private final String registerURL = "/user/register";
    private final String loginURL = "/user/login";
    private final String personURL = "/person";
    private final String eventsURL = "/event";
    private final String loadURL = "/load";
    private final String fillURL = "/fill";
    private final String clearURL = "/clear";
    private static final Key key;

    static {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public FMSServer() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.head("Closing down Server...");
            try {
                stopServer();
            } catch (IOException e) {
                Logger.severe("Unable to stop server", e);
            }
            Logger.flush();
        }));
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
            DataBase.createTables();
        } catch (IOException e) {
            Logger.severe("Unable to start server, check the log file to see what went wrong", e);
        } catch (DataBaseException e) {
            Logger.severe("Failed to create or find database, could result in severe disturbances in the functioning of the server", e);
        } finally {
            Logger.flush();
        }
    }

    private void startServer(int port) throws IOException {
        inetSocketAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(inetSocketAddress, 10);
        registerHandlers(server);
        server.start();
        this.server = server;
        this.port = port;
    }

    private void startServer() throws IOException {
        startServer(80);
    }

    private void stopServer() throws IOException {
        if (server != null)
            server.stop(0);
    }

    /**
     * Registers the handlers for the server
     * @param server server to register handler with
     */
    private void registerHandlers(HttpServer server) {
        server.createContext(registerURL, new RegisterHandler());
        server.createContext(clearURL, new ClearHandler());
        server.createContext(loginURL, new LoginHandler());
        server.createContext(personURL, new PersonHandler());
        server.createContext(eventsURL, new EventsHandler());
        server.createContext(loadURL, new LoadHandler());
        server.createContext(fillURL, new FillHandler());
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
        Logger.setLogLevel(Logger.LEVEL.FINE);
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
