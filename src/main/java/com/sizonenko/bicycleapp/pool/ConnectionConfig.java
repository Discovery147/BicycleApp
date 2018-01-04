package com.sizonenko.bicycleapp.pool;

import java.util.Properties;

public abstract class ConnectionConfig {
    public enum ConnectionType{
        MYSQL, ORACLE, POSTGRESQL;
    }

    private static final ConnectionType type = ConnectionType.MYSQL;
    private static Properties properties;

    public static void setProperties(Properties properties) {
        ConnectionConfig.properties = properties;
    }

    public static ConnectionType getType() {
        return type;
    }

    public static Properties getProperties() {
        return properties;
    }
}
