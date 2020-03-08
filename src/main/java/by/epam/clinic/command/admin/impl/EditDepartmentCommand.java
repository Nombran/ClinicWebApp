package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.DepartmentDataValidator;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code edit_department.jsp}
 * using {@code DepartmentService}.
 */
public class EditDepartmentCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_URL = "/controller?command=edit_department_page&id=";

    private static final String IF_ERROR_PAGE_URL = "/controller?command=departments_page";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String PREV_IMAGE_PATH = "prevImage";

    private static final String RESULT_PROPERTY = "result";

    private static final String SUCCESS_PROPERTY = "message.successful_edit_dep";

    private static final String FAIL_PROPERTY = "message.failed_edit_dep";

    private DepartmentServiceImpl departmentService;

    public EditDepartmentCommand() {
        this.departmentService = new DepartmentServiceImpl();
    }

    /**
     * Call method updating department based on edited info, taken from
     * {@code SessionRequestContent}and forwarding users with
     * {@link by.epam.clinic.core.model.UserRole} Admin to {@code edit_department.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object with redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page;
        if(requestContent.containsParameters(DepartmentAttribute.NAME_ATTR,
                DepartmentAttribute.DESCRIPTION_ATTR,DepartmentAttribute.PHONE_ATTR,
                DepartmentAttribute.ID_ATTR, PREV_IMAGE_PATH) &&
                requestContent.getPart(DepartmentAttribute.IMAGE_ATTR) != null) {
            long id;
            String name = requestContent.getRequestParameter(DepartmentAttribute.NAME_ATTR);
            String description = requestContent.getRequestParameter(DepartmentAttribute.DESCRIPTION_ATTR);
            String phone = requestContent.getRequestParameter(DepartmentAttribute.PHONE_ATTR);
            Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
            String idAttribute = requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR);
            try {
               id = Long.parseLong(idAttribute);
               page = PAGE_URL + id;
            } catch (NumberFormatException e) {
                requestContent.setSessionAttribute(RESULT_PROPERTY,INCORRECT_DATA_PROPERTY);
                logger.log(Level.ERROR, e);
                return new TransitionContent(IF_ERROR_PAGE_URL, TransitionType.REDIRECT);
            }
            String fileName = image.getSubmittedFileName();
            String applicationDir = requestContent.getServletContextPath();
            String prevFileName = requestContent.getRequestParameter(PREV_IMAGE_PATH);
            if (fileName.length() == 0) {
                fileName = prevFileName;
            }
            if (DepartmentDataValidator.isDataValid(name, description, phone, fileName)) {
                Department department = new Department(name, description, phone, fileName);
                department.setId(id);
                if (!fileName.equals(prevFileName)) {
                    department.setImagePath(null);
                }
                try {
                    departmentService.updateDepartment(department, applicationDir, image);
                    requestContent.setSessionAttribute(RESULT_PROPERTY, SUCCESS_PROPERTY);
                } catch (ServiceException e) {
                    logger.error(e);
                    requestContent.setSessionAttribute(RESULT_PROPERTY, FAIL_PROPERTY);
                }
            } else {
                requestContent.setSessionAttribute(RESULT_PROPERTY,INCORRECT_DATA_PROPERTY);
            }
        } else {
            page = IF_ERROR_PAGE_URL;
            requestContent.setSessionAttribute(RESULT_PROPERTY, NOT_ENOUGH_ATTRIBUTES);
        }
        return new TransitionContent(page, TransitionType.REDIRECT);
    }
}
