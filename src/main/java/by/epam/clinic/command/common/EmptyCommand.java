package by.epam.clinic.command.common;
import by.epam.clinic.command.Command;
import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * A single implementation of {@link Command} interface.
 * Command is forwarding user to {@code main_page.jsp}.
 */
public class EmptyCommand implements Command {
    private final static String PATH_PAGE_PROPERTY = "path.page.home_page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        return new TransitionContent(page,transitionType);
    }
}