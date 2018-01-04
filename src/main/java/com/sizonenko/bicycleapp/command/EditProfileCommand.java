package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Member;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.parser.MemberParser;
import com.sizonenko.bicycleapp.receiver.MemberReceiver;
import com.sizonenko.bicycleapp.validator.MemberValidator;
import org.apache.log4j.Level;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class EditProfileCommand implements Command {

    private static final String LOGIN = "login";
    private static final String PHONE = "phone";

    private MemberValidator validator = new MemberValidator();
    private MemberParser parser = new MemberParser();
    private MemberReceiver receiver = new MemberReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        String login = requestContent.getRequestParameter(LOGIN);
        String phone = requestContent.getRequestParameter(PHONE);
        Part avatar = requestContent.getFile();
        Member user = (Member) requestContent.getSessionAttribute("member");
        try {
            Member member = parser.editProfile(login, phone, avatar);
            member.setMemberId(user.getMemberId());
            if(validator.validateEditProfile(login, phone, avatar)){
                if(avatar.getSize() == 0){
                    member.setImage(user.getImage());
                }
                if(receiver.editProfile(member)){
                    user.setLogin(member.getLogin());
                    user.setPhone(member.getPhone());
                    if(avatar.getSize() != 0){
                        user.setImage(member.getImage());
                    }
                    router.setRoute(Router.RouterType.REDIRECT);
                    router.setPagePath(PageConstant.PATH_PROFILE);
                }
            }else{
                router.setPagePath(PageConstant.PATH_PROFILE);
                requestContent.setRequestAttribute("exception", "exception");
            }
        } catch (IOException | ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus", e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
