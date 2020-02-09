package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.model.DoctorAttribute;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.service.impl.UserServiceImpl;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.Optional;

public class EditDoctorPageCommand implements AdminCommand {
    private final static String PAGE_PROPERTY = "path.page.edit_doctor";

    private final static String DOCTOR_ATTR = "doctor";

    private final static String USER_ATTR = "user";

    private DoctorServiceImpl doctorService;

    private UserServiceImpl userService;

    public EditDoctorPageCommand(DoctorServiceImpl doctorService, UserServiceImpl userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        Gson gson = new Gson();
        long id = Long.parseLong(requestContent.getRequestParameter(DoctorAttribute.ID_ATTR));
        try {
            Optional<Doctor> optionalDoctor = doctorService.findDoctor(id);
            if(optionalDoctor.isPresent()) {
                Doctor doctor = optionalDoctor.get();
                String jsonDoctor = gson.toJson(doctor);
                requestContent.setRequestAttribute(DOCTOR_ATTR, jsonDoctor);
                Optional<User> optionalUser = userService.findUser(id);
                if(optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String jsonUser = gson.toJson(user);
                    requestContent.setRequestAttribute(USER_ATTR, jsonUser);
                }
            }
        } catch (ServiceException e) {
        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
