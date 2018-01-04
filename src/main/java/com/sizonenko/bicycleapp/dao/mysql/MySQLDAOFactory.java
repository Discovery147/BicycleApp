package com.sizonenko.bicycleapp.dao.mysql;

import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.dao.Table;

public class MySQLDAOFactory {
        public static AbstractDAO getDAO(Table name){
        switch (name) {
            case MEMBER:
                return new MySQLMemberDAO();
            case BICYCLE:
                return new MySQLBicycleDAO();
            case RESERVATION:
                return new MySQLReservationDAO();
            case CONFIRM:
                return new MySQLConfirmDAO();
            case TRANSACTION:
                return new MySQLTransactionDAO();
            case DOCUMENT:
                return new MySQLDoctypeDAO();
            default:
                throw new UnsupportedOperationException();
        }
    }
}
