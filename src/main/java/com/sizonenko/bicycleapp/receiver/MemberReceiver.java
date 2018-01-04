package com.sizonenko.bicycleapp.receiver;

import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.dao.AbstractMemberDAO;
import com.sizonenko.bicycleapp.dao.DAOFactory;
import com.sizonenko.bicycleapp.dao.Table;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.exception.ReceiverException;

/*
    Выбор типа DAO класса (MySQL, Oracle, ...) определяется динамическим полиморфизмом
 */

public class MemberReceiver {
    private AbstractDAO dao = DAOFactory.getDAO(Table.MEMBER);

    public boolean logIn(String login, String password) throws ReceiverException {
        try {
            return ((AbstractMemberDAO)(dao)).validateMember(login,password);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public Member getMember(String login) throws ReceiverException {
        try {
            return ((AbstractMemberDAO)(dao)).findEntityByLogin(login);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean register(Member member) throws ReceiverException {
        try {
            return dao.create(member);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean editProfile(Member member) throws ReceiverException {
        try {
            return dao.update(member);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean unblockUserByLogin(String login) throws ReceiverException {
        try {
            return ((AbstractMemberDAO)(dao)).unblockUserByLogin(login);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
