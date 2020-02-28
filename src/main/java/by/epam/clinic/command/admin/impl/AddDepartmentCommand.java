package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.DepartmentDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code add_department.jsp}
 * using {@code DepartmentService}.
 */
public class AddDepartmentCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=add_department_page";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating_dep";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating_dep";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private DepartmentServiceImpl departmentService;

    public AddDepartmentCommand() {
        this.departmentService = new DepartmentServiceImpl();
    }

    /**
     * Call method creating department based on info, taken from {@code SessionRequestContent}
     * and forwarding users with {@link by.epam.clinic.core.model.UserRole} Admin
     * to {@code add_department.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        if (requestContent.containsParameters(DepartmentAttribute.NAME_ATTR,
                DepartmentAttribute.PHONE_ATTR, DepartmentAttribute.DESCRIPTION_ATTR) &&
                requestContent.getPart(DepartmentAttribute.IMAGE_ATTR) != null) {
            Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
            String fileName = image.getSubmittedFileName();
            String applicationDir = requestContent.getServletContextPath();
            String departmentName = requestContent.getRequestParameter(DepartmentAttribute.NAME_ATTR);
            String phone = requestContent.getRequestParameter(DepartmentAttribute.PHONE_ATTR);
            String description = requestContent.getRequestParameter(DepartmentAttribute.DESCRIPTION_ATTR);
            if (DepartmentDataValidator.isDataValid(departmentName, description, phone, fileName)) {
                try {
                    departmentService.createDepartment(departmentName, description, phone, applicationDir, image);
                    requestContent.setSessionAttribute(RESULT_ATTR, SUCCESS_MESSAGE_PROPERTY);
                } catch (ServiceException e) {
                    logger.error(e);
                    requestContent.setSessionAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(PAGE_URL, TransitionType.REDIRECT);
    }
}
