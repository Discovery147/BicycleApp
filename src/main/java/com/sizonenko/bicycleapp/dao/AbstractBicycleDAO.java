package com.sizonenko.bicycleapp.dao;

import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface AbstractBicycleDAO <K> extends AbstractDAO<K, Bicycle> {
    List<Bicycle> findAllToReservation() throws DAOException;
    List<Bicycle> findAllByPlace(long memberId) throws DAOException;
}