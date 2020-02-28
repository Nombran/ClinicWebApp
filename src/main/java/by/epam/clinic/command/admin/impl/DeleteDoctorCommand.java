package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.core.model.DoctorAttribute;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code doctors.jsp}
 * using {@code DoctorService}.
 */
public class DeleteDoctorCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String RESULT_ATTR = "result";

    private static final String PAGE_URL = "/controller?command=doctors_page";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_deleting_doc";

    private static final String FAILED_DELETING = "message.failed_deleting";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_deleting_doc";

    private DoctorServiceImpl doctorService;

    public DeleteDoctorCommand() {
        this.doctorService = new DoctorServiceImpl();
    }


    /**
     * Call method which delete doctor based on doctor id, taken from {@code SessionRequestContent}
     * and forwarding users with {@link by.epam.clinic.core.model.UserRole} Admin
     * to {@code doctors.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if(requestContent.containsParameters(DoctorAttribute.ID_ATTR)) {
            String idAttribute = requestContent.getRequestParameter(DoctorAttribute.ID_ATTR);
            long id;
            try {
                id = Long.parseLong(idAttribute);
                if (doctorService.deleteDoctor(id)) {
                    requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            } catch (ServiceException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, FAILED_DELETING);
            } catch (NumberFormatException e) {
                logger.error(e);
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
    }
}
