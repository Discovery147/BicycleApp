package com.sizonenko.bicycleapp.receiver;

import com.sizonenko.bicycleapp.dao.AbstractDAO;
import com.sizonenko.bicycleapp.dao.AbstractReservationDAO;
import com.sizonenko.bicycleapp.dao.DAOFactory;
import com.sizonenko.bicycleapp.dao.Table;
import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.DAOException;
import com.sizonenko.bicycleapp.exception.ReceiverException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservationReceiver {

    private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm";
    private AbstractDAO dao = DAOFactory.getDAO(Table.RESERVATION);


    public List fillCalendarPage() throws ReceiverException {
        try {
            return ((AbstractReservationDAO)(dao)).findAllToCalendar();
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public List getUserReservations(long memberId) throws ReceiverException {
        try {
            return ((AbstractReservationDAO)(dao)).findAllByMemberId(memberId);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean createReservation(String bicycleId, String startTime, String endTime, String amount, Member member) throws ParseException, ReceiverException {
        Reservation reservation = new Reservation();
        Bicycle bicycle = new Bicycle();
        bicycle.setBicycleId(Long.parseLong(bicycleId));
        reservation.setBicycle(bicycle);
        reservation.setAmount(new BigDecimal(amount));

        SimpleDateFormat formatStart = new SimpleDateFormat(DATE_FORMAT);
        Date date = formatStart.parse(startTime);
        Timestamp timestamp = new Timestamp(date.getTime());

        reservation.setStartTime(timestamp);

        date = formatStart.parse(endTime);
        timestamp = new Timestamp(date.getTime());

        reservation.setEndTime(timestamp);
        reservation.setMember(member);

        try {
            return dao.create(reservation);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public List findAllByPlace(long memberId) throws ReceiverException {
        try {
            return ((AbstractReservationDAO)(dao)).findAllByPlace(memberId);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public Reservation findReservationById(Long id) throws ReceiverException {
        try {
            return (Reservation) dao.findEntityById(id);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
