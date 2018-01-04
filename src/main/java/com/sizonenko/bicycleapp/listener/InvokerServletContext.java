package com.sizonenko.bicycleapp.listener;

import com.sizonenko.bicycleapp.pool.ConnectionConfig;
import com.sizonenko.bicycleapp.pool.ConnectionPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;

public class InvokerServletContext implements ServletContextListener{

    private static final Logger LOGGER = Logger.getLogger(InvokerServletContext.class);
    private final String PATH_CONNECTION_PROPERTIES = "/connection_properties/";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        registerDriver();
        initializeConnectionProperties();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        closeConnections();
        deregisterDriver();
    }

    private void registerDriver() {
        try {
            BasicConfigurator.configure();
            LOGGER.log(Level.DEBUG, "REGISTER DRIVER");
            switch (ConnectionConfig.getType()) {
                case MYSQL:
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    break;
                case ORACLE:
                    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                    break;
                case POSTGRESQL:
                    Class.forName("org.postgresql.Driver").newInstance();
            }
        } catch (IllegalAccessException e) {
            LOGGER.log(Level.FATAL, "REGISTER DRIVER IS WRONG");
            throw new RuntimeException();
        } catch (InstantiationException e) {
            LOGGER.log(Level.FATAL, "REGISTER DRIVER IS WRONG");
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.FATAL, "REGISTER DRIVER IS WRONG");
            throw new RuntimeException();
        }
        LOGGER.log(Level.INFO, "REGISTER DRIVER: " + ConnectionConfig.getType().name());
    }

    private void initializeConnectionProperties(){
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(PATH_CONNECTION_PROPERTIES + ConnectionConfig.getType().name() + ".properties");
            Properties properties = new Properties();
            properties.load(input);
            ConnectionConfig.setProperties(properties);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "PATH CONNECTION PROPERTIES IS WRONG");
            throw new RuntimeException();
        }
    }

    private void closeConnections(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection;
        while(true){
            connection = pool.getConnection();
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.ERROR, "ERROR CLOSE ALL CONNECTIONS");
                }
            }else{
                break;
            }
        }
    }

    private void deregisterDriver(){
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "ERROR DEREGISTER DRIVERS");
            }
        }
    }
}
