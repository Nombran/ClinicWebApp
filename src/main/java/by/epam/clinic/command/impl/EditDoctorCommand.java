package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.service.impl.UserServiceImpl;
import by.epam.clinic.core.validator.DoctorDataValidator;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import javax.servlet.http.Part;

public class EditDoctorCommand implements AdminCommand {
    private final static String PAGE_PROPERTY = "path.page.doctors_page";

    private final static String PREV_IMAGE_PATH_ATTR = "prevImage";

    private final static String DOCTOR_ID_PARAM = "doctorId";

    private final static String SUCCESS_PROPERTY = "";

    private final static String FAILED_PROPERTY = "";

    private final static String RESULT_ATTR = "result";

    private UserServiceImpl userService;

    private DoctorServiceImpl doctorService;

    public EditDoctorCommand(UserServiceImpl userService, DoctorServiceImpl doctorService) {
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
        String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
        String email = requestContent.getRequestParameter(UserAttribute.EMAIL_ATTR);
        long userId = Long.parseLong(requestContent.getRequestParameter(UserAttribute.ID_ATTR));
        String name = requestContent.getRequestParameter(DoctorAttribute.NAME_ATTR);
        String surname = requestContent.getRequestParameter(DoctorAttribute.SURNAME_ATTR);
        String lastname = requestContent.getRequestParameter(DoctorAttribute.LASTNAME_ATTR);
        String category = requestContent.getRequestParameter(DoctorAttribute.CATEGORY_ATTR);
        String specialization = requestContent.getRequestParameter(DoctorAttribute.SPECIALIZATION_ATTR);
        long doctorId = Long.parseLong(requestContent.getRequestParameter(DOCTOR_ID_PARAM));
        long departmentId = Long.parseLong(requestContent.getRequestParameter(DoctorAttribute.DEPARTMENT_ID));
        String prevImage = requestContent.getRequestParameter(PREV_IMAGE_PATH_ATTR);
        Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
        String fileName = image.getSubmittedFileName();
        String applicationDir = requestContent.getServletContextPath();
        if(fileName.length() == 0 ) {
            fileName = prevImage;
        }
        if(UserDataValidator.isDataValid(login, password, email) &&
                DoctorDataValidator.isDataValid(name, surname, lastname, specialization, category, fileName)) {
            User user = new User(login, password, email, UserRole.DOCTOR, UserSatus.ACTIVE);
            user.setId(userId);
            Doctor doctor = new Doctor(name, surname, lastname, specialization, category, departmentId);
            doctor.setId(doctorId);
            doctor.setUserId(userId);
            if(fileName.equals(prevImage)) {
                doctor.setImagePath(fileName);
            }
            try {
                doctorService.updateDoctor(user,doctor,applicationDir,image);
                requestContent.setRequestAttribute(RESULT_ATTR,SUCCESS_PROPERTY);
            } catch (ServiceException e) {
                requestContent.setRequestAttribute(RESULT_ATTR,FAILED_PROPERTY);
                e.printStackTrace();
            }

        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
