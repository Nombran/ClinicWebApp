package by.epam.clinic.command.doctor.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
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
 * One of the implementations of {@link DoctorCommand} interface.
 * Command is forwarding user to {@code doctor_appointments.jsp}
 * using {@code AppointmentService}.
 */
public class DeleteAppointmentCommand implements DoctorCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=doctor_appointments_page";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_deleting_app";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_deleting_app";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private AppointmentServiceImpl appointmentService;

    public DeleteAppointmentCommand() {
        this.appointmentService = new AppointmentServiceImpl();
    }

    /**
     * Call method deleting appointment based on id, taken from {@code SessionRequestContent}
     * and forwarding users to {@code doctor_appointments.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if(requestContent.containsParameters(AppointmentAttribute.ID_ATTR)) {
            String idAttribute = requestContent.getRequestParameter(AppointmentAttribute.ID_ATTR);
            long appointmentId;
            User user = (User) requestContent.getSessionAttribute(CURRENT_USER_ATTR);
            try {
                appointmentId = Long.parseLong(idAttribute);
            } catch (NumberFormatException e) {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
                logger.error(e);
                return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
            }
            try {
                if (appointmentService.deleteAppointment(appointmentId, user.getId())) {
                    requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            } catch (ServiceException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
            }
        } else  {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL,TransitionType.REDIRECT);
    }
}
