package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Transaction;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.TransactionReceiver;
import org.apache.log4j.Level;

import java.math.BigDecimal;

public class CompleteTransactionCommand implements Command {

    private final String ID = "transactionId";
    private final String AMOUNT = "amount";
    private TransactionReceiver receiver = new TransactionReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(Long.valueOf(requestContent.getRequestParameter(ID)));
        transaction.setAmount(new BigDecimal(requestContent.getRequestParameter(AMOUNT)));
        try {
            if(receiver.completeTransaction(transaction)){
                router.setPagePath(PageConstant.PATH_CASHIER_FILL);
            }else{
                LOGGER.log(Level.ERROR, "Can't complete transaction");
                router.setPagePath(PageConstant.PATH_ERROR);
                requestContent.setRequestAttribute("errorStatus","MySQL Error");
                requestContent.setRequestAttribute("errorInfo", "Can't complete transaction (not found)");
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause ());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus",e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
