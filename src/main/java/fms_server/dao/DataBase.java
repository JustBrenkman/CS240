/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 4/17/19 8:20 PM
 */

package fms_server.dao;

import fms_server.exceptions.DataBaseException;
import fms_server.logging.Logger;

import java.sql.*;

public class DataBase {
    /**
     * Static connection to the database, thread safe
     */
    private static Connection connection;

    static {
        connection = null;
        try {
            //This is how we set up the driver for our database
            Logger.fine("Getting drivers");
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a connection, there can only be one connection to the database
     * @param autocommit whether or not to autocommit
     * @return a SQL connection
     * @throws DataBaseException throws error when the previous connection was not closed
     */
    public static Connection getConnection(boolean autocommit) throws DataBaseException {
        if (connection != null)
            throw new DataBaseException("Connection already in user", DataBaseException.ERROR_TYPE.CONNECTION_ALREADY_IN_USE);
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.db";
            connection = DriverManager.getConnection(CONNECTION_URL);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                Logger.fine("The driver name is " + meta.getDriverName());
                connection.setAutoCommit(autocommit);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
            throw new DataBaseException("Unable to connect to database", DataBaseException.ERROR_TYPE.SQL);
        }
    }

    /**
     * Closes the current connection and can either throw away changes or commit changes
     * @param commit whether or not to commit changes made to the database
     * @throws DataBaseException If there was no connection opened
     */
    public static void closeConnection(boolean commit) throws DataBaseException {
        if (connection == null)
            throw new DataBaseException("Connection is already closed", DataBaseException.ERROR_TYPE.CLOSED_CONNECTION);
        try {
            if (commit)
                connection.commit();
            else
                connection.rollback();
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new DataBaseException("Cannot close exception", DataBaseException.ERROR_TYPE.SQL);
        }
    }

    public static void createTables() throws DataBaseException {
        getConnection(false);
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `events` (" +
                    "`eventID`    VARCHAR( 36 )NOT NULL PRIMARY KEY UNIQUE," +
                    "`descendant` VARCHAR( 50 )," +
                    "`personID`   VARCHAR( 32 ) NOT NULL," +
                    "`latitude`   DOUBLE NOT NULL," +
                    "`longitude`  DOUBLE NOT NULL," +
                    "`country`    VARCHAR( 100 ) NOT NULL," +
                    "`city`       VARCHAR( 100 ) NOT NULL," +
                    "`eventType`  VARCHAR( 50 ) NOT NULL," +
                    "`year`       INTEGER NOT NULL, " +
                    "FOREIGN KEY (descendant) REFERENCES users(userName), " +
                    "FOREIGN KEY (personID) REFERENCES persons(personID)" +
                    ");";

            String sql_persons = "CREATE TABLE IF NOT EXISTS `persons` (\n" +
                    "`personID`\t VARCHAR( 36 ) NOT NULL PRIMARY KEY UNIQUE,\n" +
                    "`descendant` VARCHAR( 50 ),\n" +
                    "`firstName`  VARCHAR( 50 ) NOT NULL,\n" +
                    "`lastName`   VARCHAR( 50 ) NOT NULL,\n" +
                    "`gender`     VARCHAR( 1 ) NOT NULL,\n" +
                    "`father`   VARCHAR( 32 ),\n" +
                    "`mother`   VARCHAR( 32 ),\n" +
                    "`spouse`   VARCHAR( 32 )\n" +
                    ");";

            String sql_users = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "`id`  VARCHAR ( 36 ) NOT NULL UNIQUE,\n" +
                    "`email`     VARCHAR ( 50) NOT NULL UNIQUE,\n" +
                    "`userName`  VARCHAR ( 50 ) NOT NULL UNIQUE,\n" +
                    "`password`  BINARY  ( 128 ) NOT NULL,\n" +
                    "`firstName` VARCHAR ( 50 ) NOT NULL,\n" +
                    "`lastName`  VARCHAR ( 50 ) NOT NULL,\n" +
                    "`gender`    VARCHAR ( 1 ) NOT NULL,\n" +
                    "PRIMARY KEY ( `id` ),\n" +
                    "FOREIGN KEY ( `id` ) REFERENCES persons( `personID` )\n" +
                    ");";

            Logger.fine("Creating events table");
            stmt.execute(sql);
            Logger.fine("Creating persons table");
            stmt.execute(sql_persons);
            Logger.fine("Creating users");
            stmt.execute(sql_users);
        } catch (SQLException e) {
            Logger.error("Failed to create database tables", e);
            throw new DataBaseException("Something went wrong", DataBaseException.ERROR_TYPE.SQL);
        } finally {
            closeConnection(true);
        }
    }
}
