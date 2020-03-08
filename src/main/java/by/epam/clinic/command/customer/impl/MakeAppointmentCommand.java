package by.epam.clinic.command.customer.impl;

import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.core.model.AppointmentAttribute;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.AppointmentDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * One of the implementations of {@link CustomerCommand} interface.
 * Command is forwarding user to {@code make_appointment.jsp}
 * using {@code AppointmentService}.
 */
public class MakeAppointmentCommand implements CustomerCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=make_appointment_page";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_registration_ticket";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_registration_ticket";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String RESULT_ATTR = "result";

    private AppointmentServiceImpl appointmentService;

    public MakeAppointmentCommand() {
        this.appointmentService = new AppointmentServiceImpl();
    }

    /**
     * Call method creating ticket reservation based on info,
     * taken from {@code SessionRequestContent} and forwarding
     * users with {@link by.epam.clinic.core.model.UserRole} Customer
     * to {@code make_appointment.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        long appointmentId;
        if(requestContent.containsParameters(AppointmentAttribute.PURPOSE_ATTR, AppointmentAttribute.ID_ATTR)) {
            String purpose = requestContent.getRequestParameter(AppointmentAttribute.PURPOSE_ATTR);
            String stringAppointmentId = requestContent.getRequestParameter(AppointmentAttribute.ID_ATTR);
            try {
                appointmentId = Long.parseLong(stringAppointmentId);
            } catch (NumberFormatException e) {
                logger.log(Level.ERROR, e);
                requestContent.setSessionAttribute(RESULT_ATTR,INCORRECT_DATA_PROPERTY);
                return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
            }
            User user = (User) requestContent.getSessionAttribute(CURRENT_USER_ATTR);
            long userId = user.getId();
            if(!AppointmentDataValidator.isPurposeValid(purpose)) {
                requestContent.setSessionAttribute(RESULT_ATTR,INCORRECT_DATA_PROPERTY);
                return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
            }
            try {
                if (appointmentService.makeAppointment(userId, appointmentId, purpose)) {
                    requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            } catch (ServiceException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL,TransitionType.REDIRECT);
    }
}
