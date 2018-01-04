package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Place;
import com.sizonenko.bicycleapp.entity.Reservation;
import com.sizonenko.bicycleapp.exception.ReceiverException;
import com.sizonenko.bicycleapp.page.PageConstant;
import com.sizonenko.bicycleapp.parser.BicycleParser;
import com.sizonenko.bicycleapp.receiver.BicycleReceiver;
import com.sizonenko.bicycleapp.receiver.ReservationReceiver;
import org.apache.log4j.Level;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FillReservationPageCommand implements Command {

    private BicycleParser parser = new BicycleParser();
    private BicycleReceiver receiver = new BicycleReceiver();

    @Override
    public Router execute(SessionRequestContent requestContent) {
        Router router = new Router();
        try {
            List list = receiver.fillReservationPage();
            Map map = parser.transformListToMap(list);
            router.setPagePath(PageConstant.PATH_RESERVATION);
            requestContent.setRequestAttribute("data", map);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, "SQLException: " + e.getCause ());
            router.setPagePath(PageConstant.PATH_ERROR);
            requestContent.setRequestAttribute("errorStatus",e.toString());
            requestContent.setRequestAttribute("errorInfo", e.getMessage());
        }
        return router;
    }
}
