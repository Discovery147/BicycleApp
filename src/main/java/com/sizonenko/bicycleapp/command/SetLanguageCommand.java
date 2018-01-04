package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.page.PageConstant;

public class SetLanguageCommand implements Command{

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        String language = requestContent.getRequestParameter("language");
        requestContent.setSessionAttribute("languageAttr",language);
        router.setRoute(Router.RouterType.REDIRECT);
        router.setPagePath(PageConstant.PATH_INDEX);
        return router;
    }
}
