package by.epam.clinic.command.impl;

import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class GetActiveTicketsInfoCommand implements DoctorCommand {
    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String FUTURE_APPOINTMENTS_ATTR = "doctor_future_appointments";

    private static final String CUSTOMERS_ATTR = "doctor_customers";

    private static final String RESULT_ATTR = "ajax_response";

    private DoctorServiceImpl doctorService;

    public GetActiveTicketsInfoCommand(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = null;
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
            //log
        }
        return new TransitionContent(page, TransitionType.NONE);
    }
}
