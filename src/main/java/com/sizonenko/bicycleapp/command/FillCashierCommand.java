package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.BicycleReceiver;
import com.sizonenko.bicycleapp.receiver.DoctypeReceiver;
import com.sizonenko.bicycleapp.receiver.ReservationReceiver;
import com.sizonenko.bicycleapp.receiver.TransactionReceiver;
import org.apache.log4j.Level;

import java.util.List;

public class FillCashierCommand implements Command {

    private ReservationReceiver reservationReceiver = new ReservationReceiver();
    private TransactionReceiver transactionReceiver = new TransactionReceiver();
    private DoctypeReceiver doctypeReceiver = new DoctypeReceiver();
    private BicycleReceiver bicycleReceiver = new BicycleReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        Member member = (Member) requestContent.getSessionAttribute("member");
        try {
            List doctypes = doctypeReceiver.findAll();
            List bicyclesOnThisPlace = bicycleReceiver.findAllByPlace(member.getMemberId());
            List transactions = transactionReceiver.findAllByPlace(member.getMemberId());
            List reservations = reservationReceiver.findAllByPlace(member.getMemberId());
            router.setPagePath(PageConstant.PATH_CASHIER);
            requestContent.setRequestAttribute("doctypes", doctypes);
            requestContent.setRequestAttribute("transactions", transactions);
            requestContent.setRequestAttribute("bicycles", bicyclesOnThisPlace);
            requestContent.setRequestAttribute("reservations", reservations);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause ());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus",e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
