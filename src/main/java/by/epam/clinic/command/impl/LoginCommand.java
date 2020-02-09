package by.epam.clinic.command.impl;

import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.model.UserAttribute;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.service.impl.UserServiceImpl;
import by.epam.clinic.core.service.impl.UserServiceException;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import java.util.Optional;

public class LoginCommand implements GuestCommand {
    private static final String SUCCESS_USER_PROPERTY = "path.page.main_page";

    private static final String CURRENT_PAGE_PROPERTY = "path.page.login";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String LOGIN_STATUS_ATTR = "login_status";

    private static final String FAILED_LOGIN_PROPERTY = "message.user_not_found";

    private static final String ERROR_LOGIN_PROPERTY = "message.error_login";

    private static final String USER_ROLE_ATTR = "userRole";

    private UserServiceImpl userService;

    public LoginCommand(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = null;
        TransitionType transitionType = TransitionType.FORWARD;
        String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
        String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
        if(UserDataValidator.isDataValid(login,password)) {
            Optional<User> optionalUser;
            try {
                optionalUser = userService.login(login, password);
            } catch (ServiceException e) {
                page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
                requestContent.setRequestAttribute(LOGIN_STATUS_ATTR,ERROR_LOGIN_PROPERTY);
                return new TransitionContent(page,transitionType);
            }
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                page = ConfigurationManager.getProperty(SUCCESS_USER_PROPERTY);
                requestContent.setSessionAttribute(USER_ROLE_ATTR, user.getRole());
                requestContent.setSessionAttribute(CURRENT_USER_ATTR,user);
                requestContent.setSessionInvalidateStatus(false);
            } else {
                page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
                requestContent.setRequestAttribute(LOGIN_STATUS_ATTR,FAILED_LOGIN_PROPERTY);
            }
        }
        return new TransitionContent(page,transitionType);
    }
}
