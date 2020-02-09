package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.DepartmentDataValidator;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import javax.servlet.http.Part;

public class EditDepartmentCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.departments_page";

    private static final String CURRENT_PAGE_PROPERTY = "path.page.edit_department";

    private static final String PREV_IMAGE_PATH = "prevImage";

    private static final String RESULT_PROPERTY = "result";

    private static final String SUCCESS_PROPERTY = "";

    private static final String FAIL_PROPERTY = "";

    private DepartmentServiceImpl departmentService;

    public EditDepartmentCommand(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(CURRENT_PAGE_PROPERTY);
        String name = requestContent.getRequestParameter(DepartmentAttribute.NAME_ATTR);
        String description = requestContent.getRequestParameter(DepartmentAttribute.DESCRIPTION_ATTR);
        String phone = requestContent.getRequestParameter(DepartmentAttribute.PHONE_ATTR);
        Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
        long id = Long.parseLong(requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR));
        String fileName = image.getSubmittedFileName();
        String applicationDir = requestContent.getServletContextPath();
        String prevFileName = requestContent.getRequestParameter(PREV_IMAGE_PATH);
        if(fileName.length() == 0) {
            fileName = prevFileName;
        }
        if(DepartmentDataValidator.isDataValid(name,description,phone,fileName)) {
            Department department = new Department(name,description,phone,fileName);
            department.setId(id);
            if(!fileName.equals(prevFileName)) {
                department.setImagePath(null);
            }
            try {
                departmentService.updateDepartment(department,applicationDir,image);
                page = ConfigurationManager.getProperty(PAGE_PROPERTY);
                requestContent.setRequestAttribute(RESULT_PROPERTY, SUCCESS_PROPERTY);
            } catch (ServiceException e) {
                requestContent.setRequestAttribute(RESULT_PROPERTY, FAIL_PROPERTY);
            }
        } else {
            requestContent.setRequestAttribute(RESULT_PROPERTY, FAIL_PROPERTY);
        }
        return new TransitionContent(page, TransitionType.FORWARD);
    }
}
