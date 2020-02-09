package by.epam.clinic.command.impl;

import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class DoctorAppointmentsPageCommand implements DoctorCommand {
    private static final String PAGE_PROPERTY = "path.page.doctor_appointments";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        return new TransitionContent(page,transitionType);
    }
}
