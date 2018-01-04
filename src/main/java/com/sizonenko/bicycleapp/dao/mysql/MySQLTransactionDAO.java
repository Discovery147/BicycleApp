package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.sizonenko.bicycleapp.dao.AbstractTransactionDAO;
import com.sizonenko.bicycleapp.entity.*;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.pool.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLTransactionDAO implements AbstractTransactionDAO<Long>, MySQLDAO {

    private final String SQL_COMPLETE_TRANSACTION = "UPDATE transaction SET end_time = CURRENT_TIMESTAMP, amount = ? WHERE transaction_id = ?";

    @Override
    public List<Transaction> findAll() throws DAOException {
        return null;
    }

    @Override
    public Transaction findEntityById(Long id) {
       return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(Transaction entity) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Transaction entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Transaction entity) throws DAOException {
        return false;
    }

    @Override
    public List findAllByPlace(Long memberId) throws DAOException {
        List<Transaction> list = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement(); // Данные человека (если резерв), документ - залог, данные велосипеда, дата начала, примерная дата окончания и ожидаемая сумма.
            ResultSet rs = st.executeQuery("SELECT m.member_id, m.first_name, m.last_name, m.phone, d.document_id, d.number, d.other, dtype.name, " +
                    "b.bicycle_id, b.maker, b.model, b.color, b.size, t.transaction_id, t.start_time, DATE_ADD(t.start_time, INTERVAL t.period HOUR) AS end_time, t.expected_amount " +
                    "FROM transaction AS t " +
                    "LEFT JOIN member AS m ON t.member_id = m.member_id " +
                    "INNER JOIN document AS d ON t.document_id = d.document_id " +
                        "INNER JOIN doctype AS dtype ON d.doctype_id = dtype.doctype_id " +
                    "INNER JOIN bicycle AS b ON t.bicycle_id = b.bicycle_id " +
                        "INNER JOIN place AS p ON b.place_id = p.place_id WHERE t.end_time IS NULL AND p.member_id = " + memberId);
            while(rs.next()){
                Transaction transaction = new Transaction();
                Bicycle bicycle = new Bicycle();
                Member member = new Member();
                Document document = new Document();
                Doctype doctype = new Doctype();

                member.setMemberId(rs.getLong("m.member_id"));
                member.setFirstname(rs.getString("m.first_name"));
                member.setLastname(rs.getString("m.last_name"));
                member.setPhone(rs.getString("m.phone"));

                doctype.setName(rs.getString("dtype.name"));
                document.setDocumentId(rs.getLong("d.document_id"));
                document.setNumber(rs.getString("d.number"));
                document.setOther(rs.getString("d.other"));
                document.setDoctype(doctype);

                bicycle.setBicycleId(rs.getLong("b.bicycle_id"));
                bicycle.setMaker(rs.getString("b.maker"));
                bicycle.setModel(rs.getString("b.model"));
                bicycle.setColor(rs.getString("b.color"));
                bicycle.setSize(rs.getString("b.size"));

                transaction.setTransactionId(rs.getLong("t.transaction_id"));
                transaction.setStartTime(rs.getTimestamp("t.start_time"));
                transaction.setEndTime(rs.getTimestamp("end_time"));
                transaction.setExpectedAmount(rs.getBigDecimal("t.expected_amount"));

                transaction.setMember(member);
                transaction.setDocument(document);
                transaction.setBicycle(bicycle);

                list.add(transaction);
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
    public Transaction findNotFinishedTransactionById(Long id) throws DAOException {
        Transaction transaction = new Transaction();
        Connection cn = null;
        Statement st = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            st = (Statement) cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT m.member_id, m.first_name, m.last_name, m.phone, d.document_id, d.number, d.other, dtype.name, " +
                    "b.bicycle_id, b.maker, b.model, b.color, b.size, t.transaction_id, t.start_time, DATE_ADD(t.start_time, INTERVAL t.period HOUR) AS end_time, t.expected_amount " +
                    "FROM transaction AS t " +
                    "LEFT JOIN member AS m ON t.member_id = m.member_id " +
                    "INNER JOIN document AS d ON t.document_id = d.document_id " +
                    "INNER JOIN doctype AS dtype ON d.doctype_id = dtype.doctype_id " +
                    "INNER JOIN bicycle AS b ON t.bicycle_id = b.bicycle_id " +
                    "INNER JOIN place AS p ON b.place_id = p.place_id WHERE t.transaction_id = " + id);
            while(rs.next()) {
                Bicycle bicycle = new Bicycle();
                Member member = new Member();
                Document document = new Document();
                Doctype doctype = new Doctype();

                member.setMemberId(rs.getLong("m.member_id"));
                member.setFirstname(rs.getString("m.first_name"));
                member.setLastname(rs.getString("m.last_name"));
                member.setPhone(rs.getString("m.phone"));

                doctype.setName(rs.getString("dtype.name"));
                document.setDocumentId(rs.getLong("d.document_id"));
                document.setNumber(rs.getString("d.number"));
                document.setOther(rs.getString("d.other"));
                document.setDoctype(doctype);

                bicycle.setBicycleId(rs.getLong("b.bicycle_id"));
                bicycle.setMaker(rs.getString("b.maker"));
                bicycle.setModel(rs.getString("b.model"));
                bicycle.setColor(rs.getString("b.color"));
                bicycle.setSize(rs.getString("b.size"));

                transaction.setTransactionId(rs.getLong("t.transaction_id"));
                transaction.setStartTime(rs.getTimestamp("t.start_time"));
                transaction.setEndTime(rs.getTimestamp("end_time"));
                transaction.setExpectedAmount(rs.getBigDecimal("t.expected_amount"));

                transaction.setMember(member);
                transaction.setDocument(document);
                transaction.setBicycle(bicycle);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally{
            close(st);
            close(cn);
        }
        return transaction;
    }

    @Override
    public boolean completeTransaction(Transaction transaction) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        int success = 0;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_COMPLETE_TRANSACTION);
            pr.setBigDecimal(1, transaction.getAmount());
            pr.setLong(2, transaction.getTransactionId());
            success = pr.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally{
            close(pr);
            close(cn);
        }
        return success > 0;
    }
}
