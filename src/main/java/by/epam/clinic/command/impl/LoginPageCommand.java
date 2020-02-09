package by.epam.clinic.command.impl;

import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class LoginPageCommand implements GuestCommand {
    private final static String PATH_PAGE_PROPERTY = "path.page.login";

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_PROPERTY);
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
