package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
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
 * Command is forwarding user to {@code edit_department.jsp}
 * using {@code DepartmentService}.
 */
public class EditDepartmentPageCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=edit_department_page&id=";

    private static final String PAGE_PROPERTY = "path.page.edit_department";

    private static final String CURRENT_PAGE_PROPERTY = "path.page.departments_page";

    private static final String IF_ERROR_URL = "/controller?command=departments_page";

    private static final String INCORRECT_DATA_PROPERTY = "massage.failed_incorrect_data";

    private static final String NOT_ENOUGH_ATTRIBUTES = "message.failed_not_enough_attr";

    private static final String DEPARTMENT_ATTR = "department";

    private static final String FAILED_PROPERTY = "";

    private static final String RESULT_ATTR = "result";

    private DepartmentServiceImpl departmentService;

    public EditDepartmentPageCommand() {
        this.departmentService = new DepartmentServiceImpl();
    }


    /**
     * Call method creating {@link TransitionContent} object with department info
     * and forwarding users with {@link by.epam.clinic.core.model.UserRole} Admin
     * to {@code edit_department.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains forward routing type and
     * department info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        if(requestContent.containsParameters(DepartmentAttribute.ID_ATTR)) {
            Gson gson = new Gson();
            String idAttribute = requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR);
            long id;
            try {
                id = Long.parseLong(idAttribute);
            } catch (NumberFormatException e) {
                requestContent.setSessionAttribute(RESULT_ATTR, INCORRECT_DATA_PROPERTY);
                return new TransitionContent(IF_ERROR_URL, TransitionType.REDIRECT);
            }
            try {
                Optional<Department> optionalDepartment = departmentService.findDepartment(id);
                if (optionalDepartment.isPresent()) {
                    Department department = optionalDepartment.get();
                    String jsonDepartment = gson.toJson(department);
                    requestContent.setSessionAttribute(CURRENT_URL_ATTR, CURRENT_URL + id);
                    requestContent.setRequestAttribute(DEPARTMENT_ATTR, jsonDepartment);
                } else {
                    page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
                    requestContent.setRequestAttribute(RESULT_ATTR, FAILED_PROPERTY);
                }
            } catch (ServiceException e) {
                logger.error(e);
                page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
                requestContent.setRequestAttribute(RESULT_ATTR, FAILED_PROPERTY);
            }
        } else {
            requestContent.setSessionAttribute(RESULT_ATTR, NOT_ENOUGH_ATTRIBUTES);
            return new TransitionContent(IF_ERROR_URL, TransitionType.REDIRECT);
        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
