package by.epam.clinic.command.doctor.impl;

import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * One of the implementations of {@link DoctorCommand} interface.
 * Command is forwarding user to {@code doctor_appointments.jsp}
 */
public class DoctorAppointmentsPageCommand implements DoctorCommand {
    private static final String PAGE_PROPERTY = "path.page.doctor_appointments";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String  CURRENT_URL = "/controller?command=doctor_appointments_page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL);
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        return new TransitionContent(page,TransitionType.FORWARD);
    }
}
