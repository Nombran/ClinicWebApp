package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.DoctorAttribute;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.model.UserRole;
import by.epam.clinic.core.service.impl.AppointmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.List;

public class GetFreeAppointmentsCommand implements DoctorCommand, AdminCommand, CustomerCommand, GuestCommand {
    private static final String RESULT_ATTR = "ajax_response";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String ERROR_MESSAGE_PROPERTY = "";

    private AppointmentServiceImpl appointmentService;

    public GetFreeAppointmentsCommand(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = null;
        TransitionType transitionType = TransitionType.NONE;
        String param = requestContent.getRequestParameter(DoctorAttribute.ID_ATTR);
        List<Appointment> appointments;
        long doctorId;
        try {
            if (param != null) {
                doctorId = Long.parseLong(param);
                appointments = appointmentService.getDoctorsFreeAppointments(doctorId);
            } else {
                User user = (User) requestContent.getSessionAttribute(CURRENT_USER_ATTR);
                if (user.getRole() == UserRole.DOCTOR) {
                    appointments = appointmentService.findDoctorAppointments(user);
                } else {
                    requestContent.setRequestAttribute(RESULT_ATTR, ERROR_MESSAGE_PROPERTY);
                    return new TransitionContent(page, TransitionType.NONE);
                }
            }
            Gson gson = new Gson();
            String serializedData = gson.toJson(appointments);
            requestContent.setRequestAttribute(RESULT_ATTR, serializedData);
        } catch (ServiceException e) {
            requestContent.setRequestAttribute(RESULT_ATTR, ERROR_MESSAGE_PROPERTY);
        }
        return new TransitionContent(page, transitionType);
    }
}
