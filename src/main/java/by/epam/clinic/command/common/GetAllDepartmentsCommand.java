package by.epam.clinic.command.common;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Command prepare all departments information for sending
 * using {@code DepartmentService}.
 */
public class GetAllDepartmentsCommand implements DoctorCommand, AdminCommand, CustomerCommand{
    private static Logger logger = LogManager.getLogger();

    private static final String RESULT_ATTR = "ajax_response";

    private static final String ERROR_MESSAGE_PROPERTY = "message.failed_loading";

    private DepartmentServiceImpl departmentService;

    public GetAllDepartmentsCommand() {
        this.departmentService = new DepartmentServiceImpl();
    }

    /**
     * Call method creating {@link TransitionContent} object with transaction
     * type ajax, and puts all departments info into it.
     * .
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains ajax routing type and
     * departments info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        Gson gson = new Gson();
        List<Department> departments;
        try {
            departments = departmentService.getAllDepartments();
            String serializedData = gson.toJson(departments);
            requestContent.setRequestAttribute(RESULT_ATTR,serializedData);
        } catch (ServiceException e) {
            logger.error(e);
            requestContent.setRequestAttribute(RESULT_ATTR, ERROR_MESSAGE_PROPERTY);
        }
        return new TransitionContent(TransitionType.AJAX);
    }
}
