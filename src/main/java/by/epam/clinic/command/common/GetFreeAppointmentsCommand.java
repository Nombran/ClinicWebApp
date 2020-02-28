package by.epam.clinic.command.common;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Command prepare information about all free appointments
 * based on doctor id from request using {@code AppointmentService}.
 */
public class GetFreeAppointmentsCommand implements DoctorCommand, AdminCommand, CustomerCommand{
    private static Logger logger = LogManager.getLogger();

    private static final String RESULT_ATTR = "ajax_response";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String ERROR_MESSAGE_PROPERTY = "message.failed_loading";

    private AppointmentServiceImpl appointmentService;

    public GetFreeAppointmentsCommand() {
        this.appointmentService = new AppointmentServiceImpl();
    }

    /**
     * Call method creating {@link TransitionContent} object with transaction
     * type ajax, and puts information about all free doctors tickets based on
     * doctor id.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains ajax routing type and
     * appointments info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
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
                    return new TransitionContent(TransitionType.AJAX);
                }
            }
            Gson gson = new Gson();
            String serializedData = gson.toJson(appointments);
            requestContent.setRequestAttribute(RESULT_ATTR, serializedData);
        } catch (ServiceException e) {
            logger.error(e);
            requestContent.setRequestAttribute(RESULT_ATTR, ERROR_MESSAGE_PROPERTY);
        }
        return new TransitionContent(TransitionType.AJAX);
    }
}
