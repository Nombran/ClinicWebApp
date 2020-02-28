package by.epam.clinic.command.admin.impl;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * One of the implementations of {@link AdminCommand} interface.
 * Command is forwarding user to {@code add_doctor.jsp}
 * using {@code DepartmentService}.
 */
public class AddDoctorPageCommand implements AdminCommand {
    private static Logger logger = LogManager.getLogger();

    private static final String PAGE_PROPERTY = "path.page.add_doctor";

    private static final String DEPARTMENTS_ATTR = "departments";

    private static final String CURRENT_URL_ATTR = "current_page_url";

    private static final String CURRENT_URL = "/controller?command=add_doctor_page";

    private static final String IF_ERROR_URL = "/controller?command=home_page";

    private static final String RESULT_ATTR = "result";

    private static final String SERVICE_ERROR_MESSAGE = "message.failed_db_error";

    private DepartmentServiceImpl departmentService;

    public AddDoctorPageCommand() {
        this.departmentService = new DepartmentServiceImpl();
    }


    /**
     * Call method creating {@link TransitionContent} object with departments info
     * and forwarding users with {@link by.epam.clinic.core.model.UserRole} Admin
     * to {@code add_department.jsp}.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains redirect routing type and
     * departments info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        List<Department> departments;
        try {
            departments = departmentService.getAllDepartments();
        } catch (ServiceException e) {
           logger.error(e);
           requestContent.setSessionAttribute(RESULT_ATTR, SERVICE_ERROR_MESSAGE);
           return new TransitionContent(IF_ERROR_URL, TransitionType.REDIRECT);
        }
        requestContent.setSessionAttribute(CURRENT_URL_ATTR,CURRENT_URL);
        requestContent.setRequestAttribute(DEPARTMENTS_ATTR, departments);
        TransitionType transitionType = TransitionType.FORWARD;
        return new TransitionContent(page,transitionType);
    }
}
