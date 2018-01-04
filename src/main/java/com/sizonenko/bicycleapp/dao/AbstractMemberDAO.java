package com.sizonenko.bicycleapp.dao;

import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.exception.DAOException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface AbstractMemberDAO <K> extends AbstractDAO<K, Member> {

      public boolean validateMember(String login, String password) throws DAOException;
      public Member findEntityByLogin(String login) throws DAOException;
      public boolean unblockUserByLogin(String login) throws DAOException;
}
