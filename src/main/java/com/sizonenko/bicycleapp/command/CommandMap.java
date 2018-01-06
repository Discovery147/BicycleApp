package com.sizonenko.bicycleapp.command;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumMap;

public class CommandMap {
    private EnumMap<CommandType,Command> map = new EnumMap<CommandType,Command>(CommandType.class){{
        this.put(CommandType.LOG_IN, new LogInCommand());
        this.put(CommandType.LOG_OUT, new LogOutCommand());
        this.put(CommandType.LANGUAGE, new SetLanguageCommand());
        this.put(CommandType.REGISTER, new RegisterCommand());
        this.put(CommandType.FILL_MAIN, new FillMainPageCommand());
        this.put(CommandType.FILL_CALENDAR, new FillCalendarPageCommand());
        this.put(CommandType.FILL_RESERVATION, new FillReservationPageCommand());
        this.put(CommandType.RESERVATION, new ReserveCommand());
        this.put(CommandType.EDIT_PROFILE, new EditProfileCommand());
        this.put(CommandType.CONFIRM, new ConfirmCommand());
        this.put(CommandType.FILL_CASHIER, new FillCashierCommand());
        this.put(CommandType.FIND_NOT_FINISHED_TRANSACTION, new FindNotFinishedTransactionCommand());
        this.put(CommandType.FIND_RESERVATION, new FindReservationCommand());
        this.put(CommandType.COMPLETE_TRANSACTION, new CompleteTransactionCommand());
    }};
    private final static CommandMap instance = new CommandMap();
    private CommandMap(){}
    public static CommandMap getInstance(){
        return instance;
    }
    public Command get(HttpServletRequest request){
        // try catch, exception page, unknown command
        CommandType key = CommandType.valueOf(request.getParameter("command").toUpperCase());
        return map.get(key);
    }

}
