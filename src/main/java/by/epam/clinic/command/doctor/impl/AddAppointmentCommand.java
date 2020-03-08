package by.epam.clinic.command.doctor.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.AppointmentDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * One of the implementations of {@link DoctorCommand} interface.
 * Command is forwarding user to {@code doctor_appointments.jsp}
 * using {@code AppointmentService}.
 */
public class AddAppointmentCommand implements DoctorCommand {

    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=doctor_appointments_page";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating_app";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating_ticket";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String CURRENT_USER_ATTR = "current_user";


    private AppointmentServiceImpl appointmentService;

    public AddAppointmentCommand() {
        this.appointmentService = new AppointmentServiceImpl();
    }

    /**
     * Call method creating new appointment based on info,
     * taken from {@code SessionRequestContent}and forwarding
     * users to {@code doctor_appointments.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if(requestContent.containsParameters(AppointmentAttribute.DATE_TIME_ATTR)) {
            User user = (User) requestContent.getSessionAttribute(CURRENT_USER_ATTR);
            long userId = user.getId();
            String dateTime = requestContent.getRequestParameter(AppointmentAttribute.DATE_TIME_ATTR);
            LocalDateTime localDateTime;
            try {
                localDateTime = LocalDateTime.parse(dateTime);
            } catch (DateTimeParseException e) {
                logger.log(Level.ERROR, "Error in parsing date : " + dateTime,e);
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
                return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
            }
            Appointment appointment = new Appointment();
            appointment.setDateTime(localDateTime);
            try {
                if (appointmentService.createAppointment(appointment, userId)) {
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
        return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
    }
}
