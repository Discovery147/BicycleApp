package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.entity.Confirm;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.pool.ConnectionPool;
import java.sql.SQLException;
import java.util.List;

public class MySQLConfirmDAO implements AbstractDAO<Integer,Confirm>, MySQLDAO {

    private final String SQL_INSERT_CONFIRM = "INSERT INTO confirm (login, code) VALUES (?,?)";
    private final String SQL_DELETE_CONFIRM = "DELETE FROM confirm WHERE login = ? AND code = ?";

    @Override
    public List findAll() throws DAOException {
        return null;
    }

    @Override
    public Confirm findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Confirm entity) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        int success = 0;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_DELETE_CONFIRM);
            pr.setString(1, entity.getLogin());
            pr.setString(2, entity.getCode());
            success = pr.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally{
            close(pr);
            close(cn);
        }
        return success > 0;
    }

    @Override
    public boolean create(Confirm entity) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        int success = 0;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_INSERT_CONFIRM);
            pr.setString(1, entity.getLogin());
            pr.setString(2, entity.getCode());
            success = pr.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally{
            close(pr);
            close(cn);
        }
        return success > 0;
    }

    @Override
    public boolean update(Confirm entity) throws DAOException {
        return false;
    }
}
