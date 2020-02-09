package by.epam.clinic.command.impl;

import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.core.model.AppointmentAttribute;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class DeleteAppointmentCommand implements DoctorCommand {
    private static final String PAGE_PROPERTY = "path.page.doctor_appointments";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating";

    private static final String CURRENT_USER_ATTR = "current_user";

    private AppointmentServiceImpl appointmentService;

    public DeleteAppointmentCommand(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }


    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        long appointmentId = Long.parseLong(requestContent.getRequestParameter(AppointmentAttribute.ID_ATTR));
        User user = (User)requestContent.getSessionAttribute(CURRENT_USER_ATTR);
        try {
            if (appointmentService.deleteAppointment(appointmentId, user.getId())) {
                requestContent.setRequestAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
            } else {
                requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
            }
        } catch (ServiceException e) {
            requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
        }
        return new TransitionContent(page,transitionType);
    }
}
