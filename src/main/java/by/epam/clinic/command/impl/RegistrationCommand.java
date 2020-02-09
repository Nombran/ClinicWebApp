package by.epam.clinic.command.impl;

import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.CustomerServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.CustomerDataValidator;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import java.time.LocalDate;

public class RegistrationCommand implements GuestCommand {
    private static final String LOGIN_PAGE_PROPERTY = "path.page.login";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_registration";

    private static final String LOGIN_EXISTS_PROPERTY = "message.login_exists";

    private static final String BAD_PARAMETERS_PROPERTY = "message.bad_parameters";

    private static final String REGISTRATION_RESULT_ATTR = "registration_result";

    private CustomerServiceImpl customerServiceImpl;

    public RegistrationCommand(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(LOGIN_PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
        String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
        String email = requestContent.getRequestParameter(UserAttribute.EMAIL_ATTR);
        String name = requestContent.getRequestParameter(CustomerAttribute.NAME_ATTR);
        String surname = requestContent.getRequestParameter(CustomerAttribute.SURNAME_ATTR);
        String lastname = requestContent.getRequestParameter(CustomerAttribute.LASTNAME_ATTR);
        String birthday = requestContent.getRequestParameter(CustomerAttribute.BIRTHDAY);
        String phone = requestContent.getRequestParameter(CustomerAttribute.PHONE);
        if(UserDataValidator.isDataValid(login, password, email) &&
                CustomerDataValidator.isDataValid(name,surname,lastname,birthday,phone)) {
            User user = new User(login,password,email, UserRole.CUSTOMER, UserSatus.ACTIVE);
            LocalDate birthdayDate = LocalDate.parse(birthday);
            Customer customer = new Customer(name, surname, lastname, birthdayDate, phone);
            try {
                if(customerServiceImpl.createCustomer(user,customer)) {
                    requestContent.setRequestAttribute(REGISTRATION_RESULT_ATTR,SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setRequestAttribute(REGISTRATION_RESULT_ATTR,LOGIN_EXISTS_PROPERTY);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else {
            requestContent.setRequestAttribute(REGISTRATION_RESULT_ATTR,BAD_PARAMETERS_PROPERTY);
        }
        return new TransitionContent(page,transitionType);
    }
}
