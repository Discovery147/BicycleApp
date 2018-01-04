package com.sizonenko.bicycleapp.command;

import com.sizonenko.bicycleapp.controller.Router;
import com.sizonenko.bicycleapp.controller.SessionRequestContent;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public interface Command{
    Logger LOGGER = Logger.getLogger(Command.class);
    Router execute(SessionRequestContent requestContent);
}
