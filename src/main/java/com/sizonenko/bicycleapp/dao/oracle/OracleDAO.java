package com.sizonenko.bicycleapp.dao.oracle;

import com.sizonenko.bicycleapp.pool.ConnectionPool;

import java.sql.Connection; // Oracle Connection
import java.sql.SQLException;
import java.sql.Statement;

public interface OracleDAO {
    // Oracle attributes
    default void close(Statement st){
        try{
            if(st!=null){
                st.close();
            }
        } catch (SQLException e) {
            // LOG ERROR STATEMENT
        }
    }
    default void close(Connection connection){
        if(connection!=null){
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
