package com.sizonenko.bicycleapp.validator;

import com.sizonenko.bicycleapp.entity.Member;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationValidator {
    public boolean validateReservation(String bicycleId, String startTime, String endTime, String amount, Member member){
        try {
            if(member == null || member.isBlocked()) { // Если вне сессии, пользователь заблокирован
                return false;
            }
            BigDecimal amountBig = new BigDecimal(amount);
            if(amountBig.compareTo(member.getAmount()) > 0) // Если стоимость бронирования превышает баланс пользователя
            {
                return false;
            }
            Long.parseLong(bicycleId);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            Date date = format.parse(startTime);
            Timestamp timestampStart = new Timestamp(date.getTime());

            date = format.parse(endTime);
            Timestamp timestampEnd = new Timestamp(date.getTime());

            Timestamp timestampCurrent = new Timestamp(System.currentTimeMillis());

            /* Время проката мин. 1ч.
               Бронирование осуществляется минимум за 1ч. до поездки*/

            return ((timestampEnd.getTime() - timestampStart.getTime()) >= 60 * 60 * 1000 &&
                    (timestampStart.getTime() - timestampCurrent.getTime()) >= 60 * 60 * 1000);
        } catch(Exception e) {
            return false;
        }
    }
}
