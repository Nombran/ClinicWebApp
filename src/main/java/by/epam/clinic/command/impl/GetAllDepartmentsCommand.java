package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.command.DoctorCommand;
import by.epam.clinic.command.GuestCommand;
import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.service.impl.DepartmentServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;

import java.util.List;

public class GetAllDepartmentsCommand implements DoctorCommand, AdminCommand, CustomerCommand, GuestCommand {
    private static final String RESULT_ATTR = "ajax_response";

    DepartmentServiceImpl departmentService;

    public GetAllDepartmentsCommand(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = null;
        TransitionType transitionType = TransitionType.NONE;
        List<Department> departments = null;
        try {
            departments = departmentService.getAllDepartments();
        } catch (ServiceException e) {

        }
        Gson gson = new Gson();
        String serializedData = gson.toJson(departments);
        requestContent.setRequestAttribute(RESULT_ATTR,serializedData);
        return new TransitionContent(page,transitionType);
    }
}
