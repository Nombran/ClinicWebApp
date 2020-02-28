package by.epam.clinic.command;

import by.epam.clinic.command.common.EmptyCommand;
import by.epam.clinic.manager.MessageManager;
import by.epam.clinic.servlet.SessionRequestContent;


/**
 * Utility class for define command.
 */
public class ActionFactory {

    private static final String PARAMETER_COMMAND = "command";

    private static final String WRONG_ACTION_ATTRIBUTE = "wrongAction";

    private static final String INVALID_COMMAND_PROPERTY = "message.invalid_command";
    /**
     * Defines a command by its string representation that gets from request.
     * But there must be a corresponding element in enum {@code CommandType}
     *
     * @param requestContent object that contain request, response and session information.
     * @return {@code Command} object
     */
    public Command defineCommand(SessionRequestContent requestContent) {
        Command current;
        String action = requestContent.getRequestParameter(PARAMETER_COMMAND);
        if(action == null) {
            return new EmptyCommand();
        }
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            current=new EmptyCommand();
            requestContent.setRequestAttribute(WRONG_ACTION_ATTRIBUTE,
                    MessageManager.getProperty(INVALID_COMMAND_PROPERTY));
        }
        return current;
    }
}