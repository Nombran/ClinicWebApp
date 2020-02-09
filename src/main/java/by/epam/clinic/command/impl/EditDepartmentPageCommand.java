package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.Optional;

public class EditDepartmentPageCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.edit_department";

    private static final String CURRENT_PAGE_PROPERTY = "path.page.departments_page";

    private static final String DEPARTMENT_ATTR = "department";

    private static final String FAILED_PROPERTY = "";

    private static final String RESULT_ATTR = "result";

    private DepartmentServiceImpl departmentService;

    public EditDepartmentPageCommand(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        Gson gson =  new Gson();
        long id = Long.parseLong(requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR));
        try {
            Optional<Department> optionalDepartment = departmentService.findDepartment(id);
            if(optionalDepartment.isPresent()) {
                Department department = optionalDepartment.get();
                String jsonDepartment = gson.toJson(department);
                requestContent.setRequestAttribute(DEPARTMENT_ATTR, jsonDepartment);
            } else {
                page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
                requestContent.setRequestAttribute(RESULT_ATTR, FAILED_PROPERTY);
            }
        } catch (ServiceException e) {
            page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
            requestContent.setRequestAttribute(RESULT_ATTR, FAILED_PROPERTY);
        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
