package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.ReservationReceiver;
import com.sizonenko.bicycleapp.validator.ReservationValidator;
import org.apache.log4j.Level;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

public class ReserveCommand implements Command {

    private static final String START_TIME = "start";
    private static final String END_TIME = "end";
    private static final String BICYCLE_ID = "id";
    private static final String AMOUNT = "amount";

    private ReservationValidator validator = new ReservationValidator();
    private ReservationReceiver receiver = new ReservationReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        String startTime = requestContent.getRequestParameter(START_TIME);
        String endTime = requestContent.getRequestParameter(END_TIME);
        String bicycleId = requestContent.getRequestParameter(BICYCLE_ID);
        String amount = requestContent.getRequestParameter(AMOUNT);
        Member member = (Member)requestContent.getSessionAttribute("member");
        try {
            if (validator.validateReservation(bicycleId, startTime, endTime, amount, member)
                    && receiver.createReservation(bicycleId, startTime, endTime, amount, member)) {
                if (member.getDiscount() <= 40) {
                    member.setDiscount((byte) (member.getDiscount() + 5));
                }
                member.setAmount(member.getAmount().subtract(new BigDecimal(amount)));
                router.setRoute(Router.RouterType.NOTHING);
                requestContent.setRequestAttribute("content", "true");
            } else {
                router.setRoute(Router.RouterType.NOTHING);
                requestContent.setRequestAttribute("content", "false");
            }
        } catch (ParseException | ReceiverException e) {
            LOGGER.log(Level.ERROR, "Exception: " + e.getCause());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus", e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
