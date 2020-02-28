package by.epam.clinic.command.guest.impl;

import by.epam.clinic.command.guest.GuestCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * One of the implementations of {@link GuestCommand} interface.
 * Command is forwarding users to {@code login.jsp}.
 */
public class LoginPageCommand implements GuestCommand {
    private final static String PATH_PAGE_PROPERTY = "path.page.login";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=login_page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_PROPERTY);
        requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL);
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
