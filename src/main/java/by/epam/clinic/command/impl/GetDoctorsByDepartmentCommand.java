package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.List;

public class GetDoctorsByDepartmentCommand implements DoctorCommand, AdminCommand, CustomerCommand, GuestCommand {

    private DoctorServiceImpl doctorService;

    private static final String RESULT_ATTR = "ajax_response";


    public GetDoctorsByDepartmentCommand(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = null;
        TransitionType transitionType = TransitionType.NONE;
        long departmentId = Long.parseLong(requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR));
        List<Doctor> doctors = null;
        try {
            doctors = doctorService.getDoctorsByDepartmentId(departmentId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String gsonData = gson.toJson(doctors);
        requestContent.setRequestAttribute(RESULT_ATTR, gsonData);
        return new TransitionContent(page,transitionType);
    }
}
