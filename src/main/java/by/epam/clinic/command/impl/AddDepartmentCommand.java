package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.validator.DepartmentDataValidator;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import javax.servlet.http.Part;

public class AddDepartmentCommand implements AdminCommand {
    private static final String PAGE_PROPERTY = "path.page.add_department";

    private static final String RESULT_ATTR = "result";

    private static final String SUCCESS_MESSAGE_PROPERTY = "message.successful_creating";

    private static final String FAILED_MESSAGE_PROPERTY = "message.failed_creating";

    private DepartmentServiceImpl departmentService;

    public AddDepartmentCommand(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        Part image = requestContent.getPart(DepartmentAttribute.IMAGE_ATTR);
        String fileName = image.getSubmittedFileName();
        String applicationDir = requestContent.getServletContextPath();
        String departmentName = requestContent.getRequestParameter(DepartmentAttribute.NAME_ATTR);
        String phone = requestContent.getRequestParameter(DepartmentAttribute.PHONE_ATTR);
        String description = requestContent.getRequestParameter(DepartmentAttribute.DESCRIPTION_ATTR);
        if(DepartmentDataValidator.isDataValid(departmentName,description,phone,fileName)) {
            try {
                if(departmentService.createDepartment(departmentName, description, phone, applicationDir, image)) {
                    requestContent.setRequestAttribute(RESULT_ATTR,SUCCESS_MESSAGE_PROPERTY);
                } else {
                    requestContent.setRequestAttribute(RESULT_ATTR, FAILED_MESSAGE_PROPERTY);
                }
            } catch (ServiceException e) {

            }
        }
        return new TransitionContent(page,transitionType);
    }
}
