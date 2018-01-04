package com.sizonenko.bicycleapp.receiver;

import com.sizonenko.bicycleapp.dao.*;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.exception.ReceiverException;

import java.util.*;

public class BicycleReceiver {
    private AbstractDAO dao = DAOFactory.getDAO(Table.BICYCLE);

    public List fillMainPage() throws ReceiverException {
        try {
            return dao.findAll();
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public List fillReservationPage() throws ReceiverException {
        try {
            return ((AbstractBicycleDAO)(dao)).findAllToReservation();
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public List findAllByPlace(long memberId) throws ReceiverException {
        try {
            return ((AbstractBicycleDAO)(dao)).findAllByPlace(memberId);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
