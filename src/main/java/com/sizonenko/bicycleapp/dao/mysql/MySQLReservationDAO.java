package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sizonenko.bicycleapp.dao.AbstractReservationDAO;
import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.entity.Place;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.pool.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLReservationDAO implements AbstractReservationDAO<Long>, MySQLDAO {

    private final String SQL_INSERT_RESERVATION = "INSERT INTO reservation (member_id, bicycle_id, start_time, end_time, amount) VALUES (?,?,?,?,?)";
    private final String SQL_CHECK_RESERVATION = "SELECT * FROM reservation WHERE bicycle_id = ? AND NOT((start_time > ? AND start_time > ?) OR (end_time < ? AND end_time < ?))";
    private final String SQL_CHECK_TRANSACTION =  "SELECT transaction_id FROM transaction WHERE bicycle_id = ? AND ? < DATE_ADD(start_time, INTERVAL period+1 HOUR) AND end_time IS NULL";

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public List<Reservation> findAllToCalendar() throws DAOException {
        List<Reservation> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery(
                "SELECT p.name as name, p.address as address, b.maker as maker, b.model as model, b.color as color, b.size as size, r.start_time as start_time, r.end_time as end_time " +
                     "FROM reservation AS r " +
                     "INNER JOIN bicycle AS b ON r.bicycle_id = b.bicycle_id " +
                     "INNER JOIN place AS p ON b.place_id = p.place_id WHERE r.end_time > NOW()" +
                     "UNION " +
                     "SELECT p.name as name, p.address as address, b.maker as maker, b.model as model, b.color as color, b.size as size, t.start_time as start_time, DATE_ADD(t.start_time, INTERVAL t.period HOUR) as end_time " +
                     "FROM transaction AS t " +
                     "INNER JOIN bicycle AS b ON t.bicycle_id = b.bicycle_id " +
                     "INNER JOIN place AS p ON b.place_id = p.place_id WHERE DATE_ADD(t.start_time, INTERVAL t.period HOUR) > NOW() AND t.member_id IS NULL");
            while(resultSet.next()){
                Reservation reserve = new Reservation();
                Place place = new Place();
                Bicycle bicycle = new Bicycle();

                bicycle.setMaker(resultSet.getString("maker"));
                bicycle.setModel(resultSet.getString("model"));
                bicycle.setColor(resultSet.getString("color"));
                bicycle.setSize(resultSet.getString("size"));
                place.setName(resultSet.getString("name"));
                place.setAddress(resultSet.getString("address"));

                bicycle.setPlace(place);
                reserve.setStartTime(resultSet.getTimestamp("start_time"));
                reserve.setEndTime(resultSet.getTimestamp("end_time"));
                reserve.setBicycle(bicycle);

                list.add(reserve);
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
    public List<Reservation> findAllByMemberId(long memberId) throws DAOException {
        List<Reservation> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT p.address, b.maker, b.model, b.size, b.color, r.start_time, r.end_time, r.amount FROM reservation AS r " +
                            "INNER JOIN bicycle AS b ON r.bicycle_id = b.bicycle_id " +
                            "INNER JOIN place AS p ON b.place_id = p.place_id " +
                            "WHERE r.end_time > NOW() AND r.member_id = " + memberId);
            while(resultSet.next()){
                Reservation reserve = new Reservation();
                Place place = new Place();
                Bicycle bicycle = new Bicycle();

                bicycle.setMaker(resultSet.getString("b.maker"));
                bicycle.setModel(resultSet.getString("b.model"));
                bicycle.setColor(resultSet.getString("b.color"));
                bicycle.setSize(resultSet.getString("b.size"));
                place.setAddress(resultSet.getString("p.address"));

                bicycle.setPlace(place);
                reserve.setStartTime(resultSet.getTimestamp("r.start_time"));
                reserve.setEndTime(resultSet.getTimestamp("r.end_time"));
                reserve.setAmount(resultSet.getBigDecimal("r.amount"));
                reserve.setBicycle(bicycle);

                list.add(reserve);
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
    public List<Reservation> findAllByPlace(long memberId) throws DAOException {
        List<Reservation> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT m.member_id, m.first_name, m.last_name, m.phone, b.bicycle_id, b.maker, b.model, b.size, b.color, r.request_id, r.start_time, r.end_time, r.amount " +
                            "FROM reservation AS r " +
                            "INNER JOIN member AS m ON r.member_id = m.member_id " +
                            "INNER JOIN bicycle AS b ON r.bicycle_id = b.bicycle_id " +
                            "INNER JOIN place AS p ON b.place_id = p.place_id " +
                            "WHERE r.end_time > NOW() AND p.member_id = " + memberId);
            while(resultSet.next()){
                Reservation reserve = new Reservation();
                Bicycle bicycle = new Bicycle();
                Member member = new Member();

                member.setMemberId(resultSet.getLong("m.member_id"));
                member.setFirstname(resultSet.getString("m.first_name"));
                member.setLastname(resultSet.getString("m.last_name"));
                member.setPhone(resultSet.getString("m.phone"));

                bicycle.setBicycleId(resultSet.getLong("b.bicycle_id"));
                bicycle.setMaker(resultSet.getString("b.maker"));
                bicycle.setModel(resultSet.getString("b.model"));
                bicycle.setColor(resultSet.getString("b.color"));
                bicycle.setSize(resultSet.getString("b.size"));

                reserve.setReservationId(resultSet.getLong("r.request_id"));
                reserve.setStartTime(resultSet.getTimestamp("r.start_time"));
                reserve.setEndTime(resultSet.getTimestamp("r.end_time"));
                reserve.setAmount(resultSet.getBigDecimal("r.amount"));
                reserve.setBicycle(bicycle);
                reserve.setMember(member);

                list.add(reserve);
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
    public Reservation findEntityById(Long id) throws DAOException {
        Reservation reservation = new Reservation();;
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT m.*, b.*, r.*" +
                            "FROM reservation AS r " +
                            "INNER JOIN member AS m ON r.member_id = m.member_id " +
                            "INNER JOIN bicycle AS b ON r.bicycle_id = b.bicycle_id " +
                            "WHERE r.request_id = " + id);
            while(resultSet.next()){
                Bicycle bicycle = new Bicycle();
                Member member = new Member();

                member.setMemberId(resultSet.getLong("m.member_id"));
                member.setFirstname(resultSet.getString("m.first_name"));
                member.setLastname(resultSet.getString("m.last_name"));
                member.setPhone(resultSet.getString("m.phone"));

                bicycle.setBicycleId(resultSet.getLong("b.bicycle_id"));
                bicycle.setMaker(resultSet.getString("b.maker"));
                bicycle.setModel(resultSet.getString("b.model"));
                bicycle.setColor(resultSet.getString("b.color"));
                bicycle.setSize(resultSet.getString("b.size"));

                reservation.setStartTime(resultSet.getTimestamp("r.start_time"));
                reservation.setEndTime(resultSet.getTimestamp("r.end_time"));
                reservation.setAmount(resultSet.getBigDecimal("r.amount"));
                reservation.setBicycle(bicycle);
                reservation.setMember(member);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally{
            close(st);
            close(cn);
        }
        return reservation;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(Reservation entity) {
        return false;
    }

    @Override
    public boolean create(Reservation entity) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        int success = 0;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_CHECK_RESERVATION);
            pr.setLong(1,entity.getBicycle().getBicycleId());
            pr.setTimestamp(2,entity.getStartTime());
            pr.setTimestamp(3,entity.getEndTime());
            pr.setTimestamp(4,entity.getStartTime());
            pr.setTimestamp(5,entity.getEndTime());
            ResultSet resultSet = pr.executeQuery();
            while(resultSet.next()) {
                long requestId = resultSet.getLong("request_id");
                if (requestId != 0){
                     return false;
                }
            }
            pr = (PreparedStatement) cn.prepareStatement(SQL_CHECK_TRANSACTION);
            pr.setLong(1,entity.getBicycle().getBicycleId());
            pr.setTimestamp(2,entity.getStartTime());
            resultSet = pr.executeQuery();
            while(resultSet.next()) {
                long transactionId = resultSet.getByte("transaction_id");
                if (transactionId != 0){
                    return false;
                }
            }
            pr = (PreparedStatement) cn.prepareStatement(SQL_INSERT_RESERVATION);
            pr.setLong(1,entity.getMember().getMemberId());
            pr.setLong(2,entity.getBicycle().getBicycleId());
            pr.setTimestamp(3,entity.getStartTime());
            pr.setTimestamp(4,entity.getEndTime());
            pr.setBigDecimal(5,entity.getAmount());
            success = pr.executeUpdate();

            // ЕСЛИ ID ВЕЛОСИПЕДА НАЙДЕН, ТО ДАТЫ СТАРТА И ФИНИША ДОЛЖНЫ БЫТЬ ОБЕ РАНЬШЕ НАЙДЕННЫХ СТАРТА И ФИНИША ЛИБО ПОЗЖЕ

        } catch (SQLException e) {
            throw new DAOException(e);
        }finally{
            close(pr);
            close(cn);
        }
        return success > 0;
    }

    @Override
    public boolean update(Reservation entity) {
        return false;
    }
}
