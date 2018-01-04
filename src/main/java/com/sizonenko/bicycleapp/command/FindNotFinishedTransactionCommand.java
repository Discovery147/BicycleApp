package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Transaction;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.TransactionReceiver;
import org.apache.log4j.Level;

public class FindNotFinishedTransactionCommand implements Command {

    private static final String ID = "id";
    private TransactionReceiver receiver = new TransactionReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        router.setRoute(Router.RouterType.NOTHING);
        Long id = Long.valueOf(requestContent.getRequestParameter("id"));
        Transaction transaction = null;
        try {
            transaction = receiver.findNotFinishedTransactionById(id);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause ());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus",e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        requestContent.setRequestAttribute("content", transaction.toString());
        return router;
    }
}
