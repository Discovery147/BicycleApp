package com.sizonenko.bicycleapp.dao;

import com.sizonenko.bicycleapp.entity.Transaction;
import com.sizonenko.bicycleapp.exception.DAOException;

import java.util.List;

public interface AbstractTransactionDAO <K> extends AbstractDAO<K, Transaction> {
    List<Transaction> findAllByPlace(Long memberId) throws DAOException;
    Transaction findNotFinishedTransactionById(Long id) throws DAOException;
    boolean completeTransaction(Transaction transaction) throws DAOException;
}
