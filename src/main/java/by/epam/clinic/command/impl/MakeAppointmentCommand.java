package by.epam.clinic.command.impl;

import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.core.model.AppointmentAttribute;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class MakeAppointmentCommand implements CustomerCommand {
    private static final String PAGE_PROPERTY = "path.page.make_appointment";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed";

    private static final String RESULT_ATTR = "result";

    private AppointmentServiceImpl appointmentService;

    public MakeAppointmentCommand(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        String purpose = requestContent.getRequestParameter(AppointmentAttribute.PURPOSE_ATTR);
        String stringAppointmentId = requestContent.getRequestParameter(AppointmentAttribute.ID_ATTR);
        long appointmentId = Long.parseLong(stringAppointmentId);
        User user = (User)requestContent.getSessionAttribute(CURRENT_USER_ATTR);
        long userId = user.getId();
        try {
            if(appointmentService.makeAppointment(userId,appointmentId,purpose)) {
                requestContent.setRequestAttribute(RESULT_ATTR,SUCCESS_MESSAGE_PROPERTY);
            } else {
                requestContent.setRequestAttribute(RESULT_ATTR,FAILED_MESSAGE_PROPERTY);
            }
        } catch (ServiceException e) {
            requestContent.setRequestAttribute(RESULT_ATTR,FAILED_MESSAGE_PROPERTY);
        }
        return new TransitionContent(page,transitionType);
    }
}
