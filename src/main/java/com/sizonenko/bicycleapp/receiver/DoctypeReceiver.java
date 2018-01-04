package com.sizonenko.bicycleapp.receiver;

import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.dao.AbstractReservationDAO;
import com.sizonenko.bicycleapp.dao.DAOFactory;
import com.sizonenko.bicycleapp.dao.Table;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.exception.ReceiverException;

import java.util.List;

public class DoctypeReceiver {

    private AbstractDAO dao = DAOFactory.getDAO(Table.DOCUMENT);

    public List findAll() throws ReceiverException {
        try {
            return dao.findAll();
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
