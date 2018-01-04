package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.MemberReceiver;
import com.sizonenko.bicycleapp.validator.MemberValidator;
import org.apache.log4j.Level;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInCommand implements Command {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private MemberValidator validator = new MemberValidator();
    private MemberReceiver receiver = new MemberReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        String login = requestContent.getRequestParameter(PARAM_LOGIN);
        String password = requestContent.getRequestParameter(PARAM_PASSWORD);
        try {
            if(validator.validateLogIn(login, password) && receiver.logIn(login,password)){
                Member member = receiver.getMember(login);
                switch(member.getRole()){
                    case 1:
                        requestContent.setSessionAttribute("user", "user");
                        break;
                    case 2:
                        requestContent.setSessionAttribute("cashier", "cashier");
                        break;
                    case 3:
                        requestContent.setSessionAttribute("admin", "admin");
                        break;
                }
                requestContent.setSessionAttribute("member", member);
                router.setRoute(Router.RouterType.NOTHING);
                requestContent.setRequestAttribute("content", "true");
            }
            else{
                requestContent.setRequestAttribute("content", "false");
                router.setRoute(Router.RouterType.NOTHING);
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
