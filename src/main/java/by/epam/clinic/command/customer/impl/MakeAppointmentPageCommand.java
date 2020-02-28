package by.epam.clinic.command.customer.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * One of the implementations of {@link CustomerCommand} interface.
 * Command is forwarding user to {@code make_appointment.jsp}.
 */
public class MakeAppointmentPageCommand implements CustomerCommand {
    private static final String PAGE_PROPERTY = "path.page.make_appointment";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=make_appointment_page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL);
        return new TransitionContent(page,TransitionType.FORWARD);
    }
}
