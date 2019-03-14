/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/14/19 4:33 PM
 */

package fms_server;

import com.sun.net.httpserver.HttpServer;
import fms_server.dao.DataBase;
import fms_server.exceptions.DataBaseException;
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
    /**
     * The port location of the server
     */
    private int port;
    /**
     * Instance of the server
     */
    private HttpServer server;
    /**
     * InetSocketAddress of the server, holds information about the port and ip address
     */
    private InetSocketAddress inetSocketAddress;
    /**
     * Register url
     */
    private final String registerURL = "/user/register";
    /**
     * Login url
     */
    private final String loginURL = "/user/login";
    /**
     * Persons url
     */
    private final String personURL = "/person";
    /**
     * Events url
     */
    private final String eventsURL = "/event";
    /**
     * Load url
     */
    private final String loadURL = "/load";
    /**
     * fill url
     */
    private final String fillURL = "/fill";
    /**
     * clear url
     */
    private final String clearURL = "/clear";
    /**
     * Secret key for the server
     */
    private static final Key key;

    public static boolean DEBUG_MODE = true;

    static {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public FMSServer() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.head("Closing down Server...");
            stopServer();
            Logger.flush();
        }));
    }

    public static void main(String...args) {
        FMSServer server = new FMSServer();
        server.setupLogging();
        try {
            server.startServer(4201);
            Logger.head("Started server listening on http://localhost:" + server.getPort() + "/");

            // Print the list of addresses for the machine
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

    /**
     * Starts a server on a port
     * @param port port to start server on
     * @throws IOException if port os already in use
     */
    private void startServer(int port) throws IOException {
        inetSocketAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(inetSocketAddress, 10);
        registerHandlers(server);
        server.start();
        this.server = server;
        this.port = port;
    }

    /**
     * Starts server on port 80
     * @throws IOException if port is already in use
     */
    private void startServer() throws IOException {
        startServer(80);
    }

    /**
     * Stops server
     */
    private void stopServer() {
        if (server != null)
            server.stop(0);
    }

    /**
     * Registers the handlers for the server
     * @param server server to register handler with
     */
    private void registerHandlers(HttpServer server) {
        server.createContext("/", new FileHandler());
        server.createContext(registerURL, new RegisterHandler());
        server.createContext(clearURL, new ClearHandler());
        server.createContext(loginURL, new LoginHandler());
        server.createContext(personURL, new PersonHandler());
        server.createContext(eventsURL, new EventsHandler());
        server.createContext(loadURL, new LoadHandler());
        server.createContext(fillURL, new FillHandler());
    }

    /**
     * Gets the current address
     * @return address name
     */
    public String getAddress() {
        if (inetSocketAddress == null)
            return null;
        return inetSocketAddress.getHostName();
    }

    /**
     * Gets a list of address of the machine for all the interfaces
     * @return list of address
     * @throws SocketException unable to get addresses
     */
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
                final InetAddress inetAddr = addr.getAddress();

                if (!(inetAddr instanceof Inet4Address)) {
                    continue;
                }
                addresses.add(inetAddr.getHostAddress());
            }
        }
        return addresses;
    }

    /**
     * Sets up logging fot the server
     */
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

    public int getPort() {
        return port;
    }

    public static Key getKey() {
        return key;
    }
}
