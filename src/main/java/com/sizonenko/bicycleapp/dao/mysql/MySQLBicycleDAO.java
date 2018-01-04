package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.sizonenko.bicycleapp.dao.AbstractBicycleDAO;
import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Place;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.pool.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBicycleDAO implements AbstractBicycleDAO<Integer>, MySQLDAO {

    @Override
    public List<Bicycle> findAll() throws DAOException {
        List<Bicycle> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM bicycle as b INNER JOIN place as p ON b.place_id = p.place_id WHERE b.deleted = 0 AND b.place_id IS NOT NULL");
            while(resultSet.next()){
                Bicycle bicycle = new Bicycle();
                Place place = new Place();
                bicycle.setBicycleId(resultSet.getInt("bicycle_id"));
                bicycle.setSerialNumber(resultSet.getString("serial_number"));
                bicycle.setMaker(resultSet.getString("maker"));
                bicycle.setModel(resultSet.getString("model"));
                bicycle.setColor(resultSet.getString("color"));
                bicycle.setSize(resultSet.getString("size"));
                bicycle.setPrice(resultSet.getBigDecimal("price"));
                place.setPlaceId(resultSet.getInt("place_id"));
                place.setName(resultSet.getString("name"));
                place.setAddress(resultSet.getString("address"));
                bicycle.setPlace(place);
                list.add(bicycle);
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
    public Bicycle findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Bicycle entity) {
        return false;
    }

    @Override
    public boolean create(Bicycle entity) {
        return false;
    }

    @Override
    public boolean update(Bicycle entity) {
        return false;
    }

    @Override
    public List<Bicycle> findAllToReservation() throws DAOException {
        List<Bicycle> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery(
            "SELECT b.bicycle_id, b.maker, b.model, b.color, b.size, b.price, p.name, p.address " +
                    "FROM bicycle as b " +
                    "INNER JOIN place AS p ON b.place_id = p.place_id " +
                    "WHERE b.bstatus = 1 AND p.pstatus_id = 1 AND b.deleted = 0"
            );
            while(resultSet.next()){
                Bicycle bicycle = new Bicycle();
                Place place = new Place();
                bicycle.setBicycleId(resultSet.getInt("b.bicycle_id"));
                bicycle.setMaker(resultSet.getString("b.maker"));
                bicycle.setModel(resultSet.getString("b.model"));
                bicycle.setColor(resultSet.getString("b.color"));
                bicycle.setSize(resultSet.getString("b.size"));
                bicycle.setPrice(resultSet.getBigDecimal("b.price"));
                place.setName(resultSet.getString("name"));
                place.setAddress(resultSet.getString("address"));
                bicycle.setPlace(place);
                list.add(bicycle);
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
    public List findAllByPlace(long memberId) throws DAOException {
        List<Bicycle> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT b.bicycle_id, b.maker, b.model, b.color, b.size, b.price " +
                            "FROM bicycle as b " +
                            "INNER JOIN place AS p ON b.place_id = p.place_id " +
                            "WHERE b.bstatus = 1 AND p.pstatus_id = 1 AND b.deleted = 0 AND p.member_id = " + memberId +
                            " ORDER BY b.maker, b.model ASC"
            );
            while(resultSet.next()){
                Bicycle bicycle = new Bicycle();
                bicycle.setBicycleId(resultSet.getInt("b.bicycle_id"));
                bicycle.setMaker(resultSet.getString("b.maker"));
                bicycle.setModel(resultSet.getString("b.model"));
                bicycle.setColor(resultSet.getString("b.color"));
                bicycle.setSize(resultSet.getString("b.size"));
                bicycle.setPrice(resultSet.getBigDecimal("b.price"));
                list.add(bicycle);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally{
            close(st);
            close(cn);
        }
        return list;
    }
}
