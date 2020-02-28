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
 * Command is forwarding user to {@code edit_department.jsp}
 * using {@code DoctorService}.
 */
public class EditDoctorCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private final static String PAGE_URL = "/controller?command=edit_doctor_page&id=";

    private final static String IF_ERROR_PAGE_URL = "/controller?command=doctors_page";

    private final static String PREV_IMAGE_PATH_ATTR = "prevImage";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private final static String DOCTOR_ID_PARAM = "doctorId";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private final static String SUCCESS_PROPERTY = "message.successful_edit_doc";

    private final static String FAILED_PROPERTY = "message.failed_edit_doc";

    private final static String RESULT_ATTR = "result";

    private DoctorServiceImpl doctorService;

    public EditDoctorCommand() {
        this.doctorService = new DoctorServiceImpl();
    }


    /**
     * Call method updating doctor based on edited info, taken from
     * {@code SessionRequestContent} and forwarding users with
     * {@link by.epam.clinic.core.model.UserRole} Admin to {@code edit_doctor.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page;
        if(requestContent.containsParameters(UserAttribute.PASSWORD_ATTR,UserAttribute.LOGIN_ATTR,
                UserAttribute.EMAIL_ATTR, UserAttribute.ID_ATTR,DoctorAttribute.NAME_ATTR,
                DoctorAttribute.SURNAME_ATTR,DoctorAttribute.LASTNAME_ATTR,DoctorAttribute.CATEGORY_ATTR,
                DoctorAttribute.SPECIALIZATION_ATTR,DOCTOR_ID_PARAM, DoctorAttribute.DEPARTMENT_ID,
                PREV_IMAGE_PATH_ATTR) && requestContent.getPart(DepartmentAttribute.IMAGE_ATTR) != null) {
            String password = requestContent.getRequestParameter(UserAttribute.PASSWORD_ATTR);
            String login = requestContent.getRequestParameter(UserAttribute.LOGIN_ATTR);
            String email = requestContent.getRequestParameter(UserAttribute.EMAIL_ATTR);
            String userIdAttribute = requestContent.getRequestParameter(UserAttribute.ID_ATTR);
            String doctorIdAttribute = requestContent.getRequestParameter(DOCTOR_ID_PARAM);
            String departmentIdAttribute = requestContent.getRequestParameter(DoctorAttribute.DEPARTMENT_ID);
            long userId;
            long doctorId;
            long departmentId;
            try {
                userId = Long.parseLong(userIdAttribute);
                doctorId = Long.parseLong(doctorIdAttribute);
                departmentId = Long.parseLong(departmentIdAttribute);
                page = PAGE_URL + doctorId;
            } catch (NumberFormatException e) {
                logger.error(e);
                requestContent.setRequestAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
                return new TransitionContent(IF_ERROR_PAGE_URL, TransitionType.REDIRECT);
            }
            String name = requestContent.getRequestParameter(DoctorAttribute.NAME_ATTR);
            String surname = requestContent.getRequestParameter(DoctorAttribute.SURNAME_ATTR);
            String lastname = requestContent.getRequestParameter(DoctorAttribute.LASTNAME_ATTR);
            String category = requestContent.getRequestParameter(DoctorAttribute.CATEGORY_ATTR);
            String specialization = requestContent.getRequestParameter(DoctorAttribute.SPECIALIZATION_ATTR);
            String prevImage = requestContent.getRequestParameter(PREV_IMAGE_PATH_ATTR);
            Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
            String fileName = image.getSubmittedFileName();
            String applicationDir = requestContent.getServletContextPath();
            if (fileName.length() == 0) {
                fileName = prevImage;
            }
            if (UserDataValidator.isDataValid(login, password, email) &&
                    DoctorDataValidator.isDataValid(name, surname, lastname, specialization, category, fileName)) {
                User user = new User(login, password, email, UserRole.DOCTOR, UserSatus.ACTIVE);
                user.setId(userId);
                Doctor doctor = new Doctor(name, surname, lastname, specialization, category, departmentId);
                doctor.setId(doctorId);
                doctor.setUserId(userId);
                if (fileName.equals(prevImage)) {
                    doctor.setImagePath(fileName);
                }
                try {
                    doctorService.updateDoctor(user, doctor, applicationDir, image);
                    requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_PROPERTY);
                } catch (ServiceException e) {
                    logger.error(e);
                    requestContent.setSessionAttribute(RESULT_ATTR, FAILED_PROPERTY);
                }

            } else {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
            }
        } else {
            page = IF_ERROR_PAGE_URL;
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(page, TransitionType.REDIRECT);
    }
}
