package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Confirm;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.ConfirmReceiver;
import com.sizonenko.bicycleapp.receiver.MemberReceiver;
import org.apache.log4j.Level;

public class ConfirmCommand implements Command {

    private final String LOGIN = "login";
    private final String CODE = "code";
    private ConfirmReceiver confirmReceiver = new ConfirmReceiver();
    private MemberReceiver memberReceiver = new MemberReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        String login = requestContent.getRequestParameter(LOGIN);
        String code = requestContent.getRequestParameter(CODE);
        Confirm confirm = new Confirm(login, code);
        try {
            if(confirmReceiver.confirmEmail(confirm) && memberReceiver.unblockUserByLogin(confirm.getLogin())){
                requestContent.setRequestAttribute("login", login);
                router.setPagePath(PageConstant.PATH_SUCCESS_CONFIRM);
            }
            else{
                requestContent.setRequestAttribute("errorInfo", "Отказ в доступе");
                router.setPagePath(PageConstant.PATH_ERROR);
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
