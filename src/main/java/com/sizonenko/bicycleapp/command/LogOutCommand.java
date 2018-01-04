package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.page.PageConstant;

public class LogOutCommand implements Command{

    @Override
    public Router execute(SessionRequestContent requestContent) {
        requestContent.setSessionAttribute("invalidate","invalidate");
        Router router = new Router();
        router.setPagePath(PageConstant.PATH_INDEX);
        router.setRoute(Router.RouterType.REDIRECT);
        return router;
    }
}
