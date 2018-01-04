package com.sizonenko.bicycleapp.dao;

import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface AbstractReservationDAO <K> extends AbstractDAO<K, Reservation> {
    public List<Reservation> findAllToCalendar() throws DAOException;
    public List<Reservation> findAllByMemberId(long memberId) throws DAOException;
    public List<Reservation> findAllByPlace(long memberId) throws DAOException;
}
