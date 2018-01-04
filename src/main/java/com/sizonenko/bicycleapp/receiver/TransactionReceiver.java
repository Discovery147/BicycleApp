package com.sizonenko.bicycleapp.receiver;

import com.sizonenko.bicycleapp.dao.*;
import com.sizonenko.bicycleapp.entity.Transaction;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.exception.ReceiverException;

import java.util.List;

public class TransactionReceiver {
    private AbstractDAO dao = DAOFactory.getDAO(Table.TRANSACTION);

    public List findAllByPlace(long memberId) throws ReceiverException {
        try {
            return ((AbstractTransactionDAO)(dao)).findAllByPlace(memberId);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public Transaction findNotFinishedTransactionById(Long id) throws ReceiverException {
        try {
            return (Transaction) ((AbstractTransactionDAO)(dao)).findNotFinishedTransactionById(id);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean completeTransaction(Transaction transaction) throws ReceiverException {
        try {
            return ((AbstractTransactionDAO)(dao)).completeTransaction(transaction);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
