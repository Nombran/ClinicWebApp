package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code doctors.jsp}
 */
public class DoctorsPageCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.doctors_page";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=doctors_page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL);
        return new TransitionContent(page,TransitionType.FORWARD);
    }
}
