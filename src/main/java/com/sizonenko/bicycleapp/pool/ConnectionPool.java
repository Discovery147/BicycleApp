package com.sizonenko.bicycleapp.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConnectionPool {

    private static int poolSize = 10;
    private static AtomicBoolean flag = new AtomicBoolean(true);
    private static final ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private static ConcurrentLinkedDeque<Connection> queue;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (flag.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    queue = new ConcurrentLinkedDeque();
                    for (int i = 0; i < poolSize; i++) {
                        queue.offer(createConnection());
                    }
                    flag.set(false);
                }

            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public static int getPoolSize() {
        return poolSize;
    }

    public static void setPoolSize(int poolSize) {
        ConnectionPool.poolSize = poolSize;
    }

    public static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection (
                    ConnectionConfig.getProperties().getProperty("url"),
                    ConnectionConfig.getProperties().getProperty("user"),
                    ConnectionConfig.getProperties().getProperty("password")
            );
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "CONNECTION PROPERTIES IS NOT AVAILABLE");
            connection = new DefaultConnectionConfig().getConnection();
        }
        return connection;
    }

    public Connection getConnection() {
        Supplier<Connection> supplier = queue::poll;
        return supplier.get();
    }

    public void returnConnection(Connection connection) {
        Predicate<Connection> isNotNull = Objects::nonNull;
        if (isNotNull.test(connection)) {
            queue.offer(connection);
        }else{
            LOGGER.log(Level.DEBUG, "RETURNED CONNECTION IS NULL");
        }
    }
}
