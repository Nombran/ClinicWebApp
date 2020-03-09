package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.core.model.*;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.DoctorDataValidator;
import by.epam.clinic.core.validator.UserDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code add_doctor.jsp}
 * using {@code DoctorService}.
 */
public class AddDoctorCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=add_doctor_page";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating_doc";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating_doc";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String REPOSITORY_MESSAGE = "Repository error";

    private static final String LOGIN_EXISTS_PROPERTY = "message.failed_login_exists";


    private DoctorServiceImpl doctorService;

    public AddDoctorCommand() {
        this.doctorService = new DoctorServiceImpl();
    }

    /**
     * Call method creating doctor based on info, taken from {@code SessionRequestContent}
     * and forwarding users with {@link by.epam.clinic.core.model.UserRole} Admin
     * to {@code add_doctor.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if (requestContent.containsParameters(UserAttribute.LOGIN_ATTR, UserAttribute.EMAIL_ATTR,
                UserAttribute.PASSWORD_ATTR, DoctorAttribute.NAME_ATTR, DoctorAttribute.LASTNAME_ATTR,
                DoctorAttribute.SURNAME_ATTR, DoctorAttribute.SPECIALIZATION_ATTR, DoctorAttribute.CATEGORY_ATTR,
                DoctorAttribute.DEPARTMENT_ID) &&
                requestContent.getPart(DepartmentAttribute.IMAGE_ATTR) != null) {
            String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
            String email = requestContent.getRequestParameter(UserAttribute.EMAIL_ATTR);
            String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
            String name = requestContent.getRequestParameter(DoctorAttribute.NAME_ATTR);
            String lastname = requestContent.getRequestParameter(DoctorAttribute.LASTNAME_ATTR);
            String surname = requestContent.getRequestParameter(DoctorAttribute.SURNAME_ATTR);
            String specialization = requestContent.getRequestParameter(DoctorAttribute.SPECIALIZATION_ATTR);
            String category = requestContent.getRequestParameter(DoctorAttribute.CATEGORY_ATTR);
            String departmentIdAttr = requestContent.getRequestParameter(DoctorAttribute.DEPARTMENT_ID);
            long departmentId;
            try {
                departmentId = Long.parseLong(departmentIdAttr);
            } catch (NumberFormatException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
                return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
            }
            Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
            String fileName = image.getSubmittedFileName();
            String applicationDir = requestContent.getServletContextPath();
            if (UserDataValidator.isDataValid(login, password, email) &&
                    DoctorDataValidator.isDataValid(name, surname,
                            lastname, specialization, category, fileName)) {
                User user = new User(login, password, email, UserRole.DOCTOR, UserSatus.ACTIVE);
                Doctor doctor = new Doctor(name, surname, lastname, specialization, category, departmentId);
                try {
                    if(doctorService.createDoctor(user, doctor, applicationDir, image)) {
                        requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                    } else {
                        requestContent.setSessionAttribute(RESULT_ATTR, LOGIN_EXISTS_PROPERTY);
                    }
                } catch (ServiceException e) {
                    logger.error(e);
                    if (REPOSITORY_MESSAGE.equals(e.getMessage())) {
                        requestContent.setSessionAttribute(RESULT_ATTR, LOGIN_EXISTS_PROPERTY);
                    } else {
                        requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                    }
                }
            } else {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
    }
}
