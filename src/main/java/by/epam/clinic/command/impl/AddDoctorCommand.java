package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.DoctorDataValidator;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import javax.servlet.http.Part;

public class AddDoctorCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.add_doctor";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating";

    private DoctorServiceImpl doctorService;

    public AddDoctorCommand(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
        String email = requestContent.getRequestParameter(UserAttribute.EMAIL_ATTR);
        String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
        String name = requestContent.getRequestParameter(DoctorAttribute.NAME_ATTR);
        String lastname = requestContent.getRequestParameter(DoctorAttribute.LASTNAME_ATTR);
        String surname = requestContent.getRequestParameter(DoctorAttribute.SURNAME_ATTR);
        String specialization = requestContent.getRequestParameter(DoctorAttribute.SPECIALIZATION_ATTR);
        String category = requestContent.getRequestParameter(DoctorAttribute.CATEGORY_ATTR);
        long departmentId = Long.parseLong(requestContent.getRequestParameter(DoctorAttribute.DEPARTMENT_ID));
        Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
        String fileName = image.getSubmittedFileName();
        String applicationDir = requestContent.getServletContextPath();
        if(UserDataValidator.isDataValid(login, password, email) &&
                DoctorDataValidator.isDataValid(name, surname, lastname, specialization, category, fileName)) {
            User user = new User(login, password, email, UserRole.DOCTOR, UserSatus.ACTIVE);
            Doctor doctor = new Doctor(name, surname, lastname, specialization, category, departmentId);
            try {
                if(doctorService.createDoctor(user, doctor, applicationDir, image)) {
                    requestContent.setRequestAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else {
            requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
        }
        return new TransitionContent(page,transitionType);
    }
}
