package by.epam.clinic.command;

import by.epam.clinic.command.impl.EmptyCommand;
import by.epam.clinic.manager.MessageManager;
import by.epam.clinic.servlet.SessionRequestContent;


public class ActionFactory {
    public Command defineCommand(SessionRequestContent requestContent) {
        Command current = null;
        String action = requestContent.getRequestParameter("command");
        if(action == null) {
            return new EmptyCommand();
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            current=new EmptyCommand();
            requestContent.setRequestAttribute("wrongAction",
                    MessageManager.getProperty("message.invalid_command"));
        }
        return current;
    }
}