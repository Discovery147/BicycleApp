package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Place;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.parser.BicycleParser;
import com.sizonenko.bicycleapp.receiver.BicycleReceiver;
import org.apache.log4j.Level;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FillMainPageCommand implements Command {

    private BicycleParser parser = new BicycleParser();
    private BicycleReceiver receiver = new BicycleReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        try {
            List list = receiver.fillMainPage();
            Map map = parser.transformListToMap(list);
            requestContent.setRequestAttribute("data", map);
            router.setPagePath(PageConstant.PATH_MAIN);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause ());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus",e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
