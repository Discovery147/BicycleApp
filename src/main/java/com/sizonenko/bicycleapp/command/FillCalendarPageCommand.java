package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.ReservationReceiver;
import org.apache.log4j.Level;
import java.sql.SQLException;
import java.util.List;

public class FillCalendarPageCommand implements Command {

    private ReservationReceiver receiver = new ReservationReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        List allReservations, userReservations;
        try{
            allReservations = receiver.fillCalendarPage();
            Member member = (Member) requestContent.getSessionAttribute("member");
            if(member != null){
                userReservations = receiver.getUserReservations(member.getMemberId());
                requestContent.setRequestAttribute("data_user", userReservations);
            }
            router.setPagePath(PageConstant.PATH_CALENDAR);
            requestContent.setRequestAttribute("data", allReservations);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause ());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus",e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
