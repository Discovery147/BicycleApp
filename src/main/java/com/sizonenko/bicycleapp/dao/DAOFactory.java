package com.sizonenko.bicycleapp.dao;

import com.sizonenko.bicycleapp.dao.mysql.MySQLDAOFactory;
import com.sizonenko.bicycleapp.pool.ConnectionConfig;

public abstract class DAOFactory {
    public static AbstractDAO getDAO(Table name){
        switch(ConnectionConfig.getType()) {
            case MYSQL:
                return MySQLDAOFactory.getDAO(name);
            case ORACLE:
                throw new UnsupportedOperationException();
            case POSTGRESQL:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }

    }
}
