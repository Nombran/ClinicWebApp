package by.epam.clinic.command.doctor.impl;

import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * One of the implementations of {@link DoctorCommand} interface.
 * Command prepare all doctor's active tickets information for sending
 * using {@code DoctorService}.
 */
public class GetActiveTicketsInfoCommand implements DoctorCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String FUTURE_APPOINTMENTS_ATTR = "doctor_future_appointments";

    private static final String CUSTOMERS_ATTR = "doctor_customers";

    private static final String RESULT_ATTR = "ajax_response";

    private static final String ERROR_MESSAGE_PROPERTY = "message.failed_loading";

    private DoctorServiceImpl doctorService;

    public GetActiveTicketsInfoCommand() {
        this.doctorService = new DoctorServiceImpl();
    }

    /**
     * Call method creating {@link TransitionContent} object with transaction
     * type ajax, and puts all doctor's active tickets info into it.
     * .
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains ajax routing type and
     * appointments info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        Gson gson = new Gson();
        User user = (User)requestContent.getSessionAttribute(CURRENT_USER_ATTR);
        List<Appointment> futureAppointments;
        List<Customer> futureCustomers;
        long userId = user.getId();
        try {
            futureAppointments = doctorService.findDoctorFutureAppointments(userId);
            futureCustomers = doctorService.findDoctorsFutureCustomers(userId);
            String jsonFutureAppointments = gson.toJson(futureAppointments);
            String jsonFutureCustomers = gson.toJson(futureCustomers);
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put(FUTURE_APPOINTMENTS_ATTR,jsonFutureAppointments);
            attributes.put(CUSTOMERS_ATTR,jsonFutureCustomers);
            String ajaxResult = gson.toJson(attributes);
            requestContent.setRequestAttribute(RESULT_ATTR, ajaxResult);
        } catch (ServiceException e) {
            logger.error(e);
            requestContent.setRequestAttribute(RESULT_ATTR, ERROR_MESSAGE_PROPERTY);
        }
        return new TransitionContent(TransitionType.AJAX);
    }
}
