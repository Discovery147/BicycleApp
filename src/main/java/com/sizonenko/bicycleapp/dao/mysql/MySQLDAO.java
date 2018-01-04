package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.sizonenko.bicycleapp.pool.ConnectionPool;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

public interface MySQLDAO{
    // MySQL attributes
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
