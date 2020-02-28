package by.epam.clinic.command.common;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.command.guest.GuestCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * Command is forwarding users to {@code main_page.jsp}
 */
public class HomePageCommand implements DoctorCommand, AdminCommand, CustomerCommand, GuestCommand {
    private static final String PAGE_PROPERTY = "path.page.home_page";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=home_page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL);
        return new TransitionContent(page,TransitionType.FORWARD);
    }
}
