package by.epam.clinic.command.guest.impl;

import by.epam.clinic.command.guest.GuestCommand;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.model.UserAttribute;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.service.impl.UserServiceImpl;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * One of the implementations of {@link GuestCommand} interface.
 * Command is processing login requests.
 * using {@code UserService}.
 */
public class LoginCommand implements GuestCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String CURRENT_PAGE_URL = "/controller?command=login_page";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String MAIN_PAGE_URL = "/controller?command=home_page";

    private static final String RESULT_ATTR = "result";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String FAILED_LOGIN_PROPERTY = "message.user_not_found";

    private static final String ERROR_LOGIN_PROPERTY = "message.error_login";

    private static final String USER_ROLE_ATTR = "userRole";

    private UserServiceImpl userService;

    public LoginCommand() {
        this.userService = new UserServiceImpl();
    }

    /**
     * Call method checks user's login and password based on info, taken
     * from {@code SessionRequestContent} and forwarding users with
     * correct account data to {@code main_page.jsp}. Besides this
     * method puts user's data into session.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if(requestContent.containsParameters(UserAttribute.LOGIN_ATTR,UserAttribute.PASSWORD_ATTR)) {
            String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
            String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
            if (UserDataValidator.isDataValid(login, password)) {
                Optional<User> optionalUser;
                try {
                    optionalUser = userService.login(login, password);
                } catch (ServiceException e) {
                    logger.error(e);
                    requestContent.setSessionAttribute(RESULT_ATTR, ERROR_LOGIN_PROPERTY);
                    return new TransitionContent(CURRENT_PAGE_URL, TransitionType.REDIRECT);
                }
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    requestContent.setSessionAttribute(USER_ROLE_ATTR, user.getRole());
                    requestContent.setSessionAttribute(CURRENT_USER_ATTR, user);
                    requestContent.setSessionAttribute(CURRENT_URL_ATTR, MAIN_PAGE_URL);
                    return new TransitionContent(MAIN_PAGE_URL, TransitionType.REDIRECT);
                } else {
                    requestContent.setSessionAttribute  (RESULT_ATTR, FAILED_LOGIN_PROPERTY);
                }
            } else {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(CURRENT_PAGE_URL,TransitionType.REDIRECT);
    }
}
