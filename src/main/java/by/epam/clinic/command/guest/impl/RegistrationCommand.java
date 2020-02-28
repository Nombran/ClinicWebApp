package by.epam.clinic.command.guest.impl;

import by.epam.clinic.command.guest.GuestCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.CustomerServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.CustomerDataValidator;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

/**
 * One of the implementations of {@link GuestCommand} interface.
 * Command is processing registration requests using {@code CustomerService}.
 */
public class RegistrationCommand implements GuestCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=login_page";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_registration";

    private static final String LOGIN_EXISTS_PROPERTY = "message.failed_login_exists";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String RESULT_ATTR = "result";

    private static final String SERVICE_ERROR_MESSAGE = "message.failed_db_error";

    private CustomerServiceImpl customerServiceImpl;

    public RegistrationCommand() {
        this.customerServiceImpl = new CustomerServiceImpl();
    }

    /**
     * Call method creates new user account based on info, taken from {@code SessionRequestContent}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if(requestContent.containsParameters(UserAttribute.LOGIN_ATTR, UserAttribute.PASSWORD_ATTR,
                UserAttribute.EMAIL_ATTR, CustomerAttribute.NAME_ATTR,CustomerAttribute.SURNAME_ATTR,
                CustomerAttribute.LASTNAME_ATTR,CustomerAttribute.BIRTHDAY,CustomerAttribute.PHONE)) {
            String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
            String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
            String email = requestContent.getRequestParameter(UserAttribute.EMAIL_ATTR);
            String name = requestContent.getRequestParameter(CustomerAttribute.NAME_ATTR);
            String surname = requestContent.getRequestParameter(CustomerAttribute.SURNAME_ATTR);
            String lastname = requestContent.getRequestParameter(CustomerAttribute.LASTNAME_ATTR);
            String birthday = requestContent.getRequestParameter(CustomerAttribute.BIRTHDAY);
            String phone = requestContent.getRequestParameter(CustomerAttribute.PHONE);
            if (UserDataValidator.isDataValid(login, password, email) &&
                    CustomerDataValidator.isDataValid(name, surname, lastname, birthday, phone)) {
                User user = new User(login, password, email, UserRole.CUSTOMER, UserSatus.ACTIVE);
                LocalDate birthdayDate = LocalDate.parse(birthday);
                Customer customer = new Customer(name, surname, lastname, birthdayDate, phone);
                try {
                    if (customerServiceImpl.createCustomer(user, customer)) {
                        requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                    } else {
                        requestContent.setSessionAttribute(RESULT_ATTR, LOGIN_EXISTS_PROPERTY);
                    }
                } catch (ServiceException e) {
                    logger.error(e);
                    requestContent.setSessionAttribute(RESULT_ATTR, SERVICE_ERROR_MESSAGE);
                }
            } else {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL,TransitionType.REDIRECT);
    }
}
