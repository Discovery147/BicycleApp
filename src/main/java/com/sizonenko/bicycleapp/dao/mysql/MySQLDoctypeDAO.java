package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Doctype;
import com.sizonenko.bicycleapp.entity.Place;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.pool.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLDoctypeDAO implements AbstractDAO<Integer,Doctype>, MySQLDAO{
    @Override
    public List<Doctype> findAll() throws DAOException {
        List<Doctype> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM doctype WHERE deleted = 0");
            while(rs.next()){
                Doctype doctype = new Doctype();
                doctype.setDoctypeId(rs.getLong("doctype_id"));
                doctype.setName(rs.getString("name"));
                list.add(doctype);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally{
            close(st);
            close(cn);
        }
        return list;
    }

    @Override
    public Doctype findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Doctype entity) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Doctype entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Doctype entity) throws DAOException {
        return false;
    }
}
