package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class DeleteDepartmentCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.departments_page";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_PROPERTY = "";

    private static final String FAILED_PROPERTY = "";

    private DepartmentServiceImpl departmentService;

    public DeleteDepartmentCommand(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        long id = Long.parseLong(requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR));
        try {
            departmentService.deleteDepartment(id);
            requestContent.setRequestAttribute(RESULT_ATTR,SUCCESS_PROPERTY);
        } catch (ServiceException e) {
            requestContent.setRequestAttribute(RESULT_ATTR,FAILED_PROPERTY);
        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
