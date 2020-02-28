package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code edit_doctor.jsp}
 * using {@code DoctorService}.
 */
public class EditDoctorPageCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=edit_doctor_page&id=";

    private static final String IF_ERROR_URL = "/controller?command=doctors_page";

    private final static String PAGE_PROPERTY = "path.page.edit_doctor";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String SERVICE_ERROR_MESSAGE = "message.failed_db_error";

    private final static String RESULT_ATTR = "result";

    private final static String DOCTOR_ATTR = "doctor";

    private final static String USER_ATTR = "user";

    private DoctorServiceImpl doctorService;

    private UserServiceImpl userService;

    public EditDoctorPageCommand() {
        this.doctorService = new DoctorServiceImpl();
        this.userService = new UserServiceImpl();
    }

    /**
     * Call method creating {@link TransitionContent} object with doctor info
     * and forwarding users with {@link by.epam.clinic.core.model.UserRole} Admin
     * to {@code edit_doctor.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains forward routing type and
     * doctor info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        Gson gson = new Gson();
        if(requestContent.containsParameters(DoctorAttribute.ID_ATTR)) {
            String idAttribute = requestContent.getRequestParameter(DoctorAttribute.ID_ATTR);
            long id;
            try {
                id = Long.parseLong(idAttribute);
            } catch (NumberFormatException e) {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
                return new TransitionContent(IF_ERROR_URL, TransitionType.REDIRECT);
            }
            try {
                Optional<Doctor> optionalDoctor = doctorService.findDoctor(id);
                if (optionalDoctor.isPresent()) {
                    Doctor doctor = optionalDoctor.get();
                    String jsonDoctor = gson.toJson(doctor);
                    requestContent.setRequestAttribute(DOCTOR_ATTR, jsonDoctor);
                    Optional<User> optionalUser = userService.findUser(id);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        String jsonUser = gson.toJson(user);
                        requestContent.setRequestAttribute(USER_ATTR, jsonUser);
                        requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL + id);
                    }
                }
            } catch (ServiceException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, SERVICE_ERROR_MESSAGE);
                return new TransitionContent(IF_ERROR_URL, TransitionType.REDIRECT);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
            return new TransitionContent(IF_ERROR_URL, TransitionType.REDIRECT);
        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
