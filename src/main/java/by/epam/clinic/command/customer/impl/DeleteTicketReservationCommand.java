package by.epam.clinic.command.customer.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.core.model.AppointmentAttribute;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * One of the implementations of {@link CustomerCommand} interface.
 * Command is forwarding user to {@code personal_page.jsp}
 * using {@code AppointmentService}.
 */
public class DeleteTicketReservationCommand implements CustomerCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=personal_customer_page";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_deleting_ticket";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_deleting_ticket";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private AppointmentServiceImpl appointmentService;

    public DeleteTicketReservationCommand() {
        this.appointmentService = new AppointmentServiceImpl();
    }

    /**
     * Call method deletes ticket reservation  based on appointment info,
     * taken from {@code SessionRequestContent} and forwards to {@code add_department.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        User user = (User)requestContent.getSessionAttribute(CURRENT_USER_ATTR);
        if(requestContent.containsParameters(AppointmentAttribute.ID_ATTR)) {
            String idAttr = requestContent.getRequestParameter(AppointmentAttribute.ID_ATTR);
            long appointmentId;
            try {
                appointmentId = Long.parseLong(idAttr);
                appointmentService.deleteTicketReservation(user.getId(), appointmentId);
                requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
            } catch (ServiceException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
            } catch (NumberFormatException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
    }
}
