package by.epam.clinic.command.impl;

import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import java.time.LocalDateTime;

public class AddAppointmentCommand implements DoctorCommand {
    private static final String PAGE_PROPERTY = "path.page.doctor_appointments";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String APPOINTMENTS_ATTR = "doctors_appointments";

    private AppointmentServiceImpl appointmentService;

    public AddAppointmentCommand(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        if(requestContent.containsParameters(AppointmentAttribute.DATE_TIME_ATTR)) {
            User user = (User) requestContent.getSessionAttribute(CURRENT_USER_ATTR);
            long userId = user.getId();
            String dateTime = requestContent.getRequestParameter(AppointmentAttribute.DATE_TIME_ATTR);
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
            Appointment appointment = new Appointment();
            appointment.setDateTime(localDateTime);
            try {
                if (appointmentService.createAppointment(appointment, userId)) {
                    requestContent.setRequestAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            } catch (ServiceException e) {
                requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
            }
        } else {

        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
