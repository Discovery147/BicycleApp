package com.sizonenko.bicycleapp.dao.mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sizonenko.bicycleapp.dao.AbstractMemberDAO;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.pool.ConnectionPool;
import com.sizonenko.bicycleapp.entity.Member;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySQLMemberDAO implements AbstractMemberDAO<Integer>, MySQLDAO {

    private final String SQL_CHECK_MEMBER_BY_LOGIN_AND_PASSWORD = "SELECT member_id FROM member WHERE login = ? AND password = MD5(?) AND deleted = 0";
    private final String SQL_SELECT_MEMBER_BY_LOGIN = "SELECT * FROM member WHERE login=?";
    private final String SQL_INSERT_MEMBER = "INSERT INTO member (first_name, last_name, phone, email, login, password) VALUES (?,?,?,?,?,MD5(?))";
    private final String SQL_UPDATE_PROFILE = "UPDATE member SET login = ?, phone = ?, image = ? WHERE member_id = ?";
    private final String SQL_UNBLOCK_USER = "UPDATE member SET blocked = 0 WHERE login = ?";

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public boolean validateMember(String login, String password) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        boolean result = false;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_CHECK_MEMBER_BY_LOGIN_AND_PASSWORD);
            pr.setString(1, login);
            pr.setString(2, password);
            ResultSet resultSet = pr.executeQuery();
            while(resultSet.next()){
                int memberId = resultSet.getInt("member_id");
                if (memberId != 0){
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally{
            close(pr);
            close(cn);
        }
        return result;
    }

    @Override
    public Member findEntityByLogin(String login) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        Member member = new Member();
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_SELECT_MEMBER_BY_LOGIN);
            pr.setString(1, login);
            ResultSet resultSet = pr.executeQuery();
            while(resultSet.next()){
                member.setRole(resultSet.getByte("role_id"));
                member.setMemberId(resultSet.getLong("member_id"));
                member.setDiscount(resultSet.getByte("discount"));
                member.setFirstname(resultSet.getString("first_name"));
                member.setLastname(resultSet.getString("last_name"));
                member.setPhone(resultSet.getString("phone"));
                member.setEmail(resultSet.getString("email"));
                member.setLogin(resultSet.getString("login"));
                member.setAmount(resultSet.getBigDecimal("amount"));
                member.setBlocked(resultSet.getBoolean("blocked"));
                member.setImage(resultSet.getString("image"));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally{
            close(pr);
            close(cn);
        }
        return member;
    }

    @Override
    public boolean unblockUserByLogin(String login) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        int success = 0;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_UNBLOCK_USER);
            pr.setString(1, login);
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
    public Member findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Member entity) {
        return false;
    }

    @Override
    public boolean create(Member entity) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        int success = 0;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_INSERT_MEMBER);
            pr.setString(1, entity.getFirstname());
            pr.setString(2, entity.getLastname());
            pr.setString(3, entity.getPhone());
            pr.setString(4, entity.getEmail());
            pr.setString(5, entity.getLogin());
            pr.setString(6, entity.getPassword());
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
    public boolean update(Member entity) throws DAOException {
        Connection cn = null;
        PreparedStatement pr = null;
        try{
            cn = (Connection) ConnectionPool.getInstance().getConnection();
            pr = (PreparedStatement) cn.prepareStatement(SQL_UPDATE_PROFILE);
            pr.setString(1, entity.getLogin());
            pr.setString(2, entity.getPhone());
            byte[] byteConent = entity.getImage().getBytes();
            pr.setBlob(3, new SerialBlob(byteConent));
            pr.setLong(4, entity.getMemberId());
            int i = pr.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally{
            close(pr);
            close(cn);
        }
    }
}
