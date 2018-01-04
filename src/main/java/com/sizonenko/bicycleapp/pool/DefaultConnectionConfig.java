package com.sizonenko.bicycleapp.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DefaultConnectionConfig {

    private static final Logger LOGGER = Logger.getLogger(DefaultConnectionConfig.class);
    private final String URL = "jdbc:mysql://localhost:3306/bicycle_db";
    private final String USER = "root";
    private final String PASSWORD = "8774460";

    public Connection getConnection() {
        Connection connection = null;
        try {
             return connection = DriverManager.getConnection (URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "DEFAULT CONNECTION PROPERTIES IS NOT AVAILABLE");
            throw new RuntimeException();
        }
    }
}
