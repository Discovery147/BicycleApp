package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.api.mail.MailSender;
import com.sizonenko.bicycleapp.api.sms.SmsSender;
import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Confirm;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.receiver.ConfirmReceiver;
import com.sizonenko.bicycleapp.receiver.MemberReceiver;
import com.sizonenko.bicycleapp.validator.MemberValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Level;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RegisterCommand implements Command {

    private static final String PARAM_FIRSTNAME = "firstname";
    private static final String PARAM_LASTNAME = "lastname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PHONE = "phone";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    private MemberValidator validator = new MemberValidator();
    private MemberReceiver receiver = new MemberReceiver();
    private ConfirmReceiver confirmReceiver = new ConfirmReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        Member member = new Member();
        member.setFirstname(requestContent.getRequestParameter(PARAM_FIRSTNAME));
        member.setLastname(requestContent.getRequestParameter(PARAM_LASTNAME));
        member.setPhone(requestContent.getRequestParameter(PARAM_PHONE));
        member.setEmail(requestContent.getRequestParameter(PARAM_EMAIL));
        member.setLogin(requestContent.getRequestParameter(PARAM_LOGIN));
        member.setPassword(requestContent.getRequestParameter(PARAM_PASSWORD));
        try {
            if (validator.validateRegister(member) && receiver.register(member)) {
                String uniqueCode = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
                Confirm confirm = new Confirm(member.getLogin(), uniqueCode);
                if(confirmReceiver.createConfirm(confirm)){
                    // SmsSender.send(confirm, member.getPhone());
                    new MailSender().send(confirm, member.getEmail());
                }
                router.setRoute(Router.RouterType.NOTHING);
                requestContent.setRequestAttribute("content", "true");
            } else {
                router.setRoute(Router.RouterType.NOTHING);
                requestContent.setRequestAttribute("content", "false");
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus", e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
