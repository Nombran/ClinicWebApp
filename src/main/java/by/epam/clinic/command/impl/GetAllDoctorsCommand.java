package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.List;

public class GetAllDoctorsCommand implements AdminCommand, CustomerCommand {
    private static final String RESULT_ATTR = "ajax_response";

    private DoctorServiceImpl doctorService;

    public GetAllDoctorsCommand(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        TransitionType transitionType = TransitionType.NONE;
        try {
            List<Doctor> doctors = doctorService.findAllDoctors();
            Gson gson = new Gson();
            String jsonData = gson.toJson(doctors);
            requestContent.setRequestAttribute(RESULT_ATTR,jsonData);
        } catch (ServiceException e) {

        }
        return new TransitionContent(null,transitionType);
    }
}
