package com.sizonenko.bicycleapp.receiver;

import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.dao.DAOFactory;
import com.sizonenko.bicycleapp.dao.Table;
import com.sizonenko.bicycleapp.entity.Confirm;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.exception.ReceiverException;

public class ConfirmReceiver {

    private AbstractDAO dao = DAOFactory.getDAO(Table.CONFIRM);

    public boolean createConfirm(Confirm confirm) throws ReceiverException {
        try {
            return dao.create(confirm);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean confirmEmail(Confirm confirm) throws ReceiverException {
        try {
            return dao.delete(confirm);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
