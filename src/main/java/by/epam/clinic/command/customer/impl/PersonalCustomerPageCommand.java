package by.epam.clinic.command.customer.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.CustomerServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * One of the implementations of {@link CustomerCommand} interface.
 * Command is forwarding user to {@code personal_page.jsp}
 * using {@code CustomerService}.
 */
public class PersonalCustomerPageCommand implements CustomerCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=personal_customer_page";

    private static final String PAGE_PROPERTY = "path.page.personal_page";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String APPOINTMENTS_ATTR = "appointments";

    private static final String RESULT_ATTR = "result";

    private static final String DOCTORS_ATTR = "doctors";

    private static final String SERVICE_ERROR_MESSAGE = "message.failed_db_error";

    private CustomerServiceImpl customerServiceImpl;

    public PersonalCustomerPageCommand() {
        this.customerServiceImpl = new CustomerServiceImpl();
    }

    /**
     * Call method puts info of all user's active tickets
     * and forwarding to {@code personal_page.jsp}.
     *
     * @param requestContent object of that contain request, response
     * and session information.
     * @return {@link TransitionContent} object with forward routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        User user = (User)requestContent.getSessionAttribute(CURRENT_USER_ATTR);
        long userId = user.getId();
        try {
            List[] appointmentsData = customerServiceImpl.findActiveAppointments(userId);
            List appointments = appointmentsData[0];
            List doctors = appointmentsData[1];
            requestContent.setRequestAttribute(APPOINTMENTS_ATTR, appointments);
            requestContent.setRequestAttribute(DOCTORS_ATTR, doctors);
            requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL);
        } catch (ServiceException e) {
            logger.error(e);
            requestContent.setSessionAttribute(RESULT_ATTR, SERVICE_ERROR_MESSAGE);
        }
        return new TransitionContent(page,transitionType);
    }
}
