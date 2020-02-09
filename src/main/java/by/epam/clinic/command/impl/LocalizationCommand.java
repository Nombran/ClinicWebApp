package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class LocalizationCommand implements DoctorCommand, AdminCommand, CustomerCommand, GuestCommand {
    private static final String LOCALIZATION_ATTR = "language";

    private static final String PAGE_ATTR = "page";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = requestContent.getRequestParameter(PAGE_ATTR);
        TransitionType transitionType = TransitionType.FORWARD;
        String localization = requestContent.getRequestParameter(LOCALIZATION_ATTR);
        requestContent.setSessionAttribute(LOCALIZATION_ATTR,localization);
        return new TransitionContent(page,transitionType);
    }
}
