package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import java.util.List;

public class AddDoctorPageCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.add_doctor";

    private static final String DEPARTMENTS_ATTR = "departments";

    private DepartmentServiceImpl departmentService;

    public AddDoctorPageCommand(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        List<Department> departments = null;
        try {
            departments = departmentService.getAllDepartments();
        } catch (ServiceException e) {
            //log
        }
        requestContent.setRequestAttribute(DEPARTMENTS_ATTR, departments);
        return new TransitionContent(page,transitionType);
    }
}
