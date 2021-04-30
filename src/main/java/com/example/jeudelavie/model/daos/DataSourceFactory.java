package com.example.jeudelavie.model.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceFactory {
    /**
     * Connection to the DB
     */
    private DataSourceFactory() {

        throw new IllegalStateException("This is a static class that should not be instantiated");
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection getConnection() throws SQLException {
        Connection cnx = DriverManager.getConnection("jdbc:sqlite:sqlite.db");
        return cnx;
    }
}
