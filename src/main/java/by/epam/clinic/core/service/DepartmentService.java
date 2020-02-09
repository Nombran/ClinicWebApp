package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.service.impl.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    boolean createDepartment(String name, String description, String phone,
                             String servletContextPath, Part imageFile) throws ServiceException;

    List<Department> getAllDepartments() throws ServiceException;

    void deleteDepartment(long id) throws ServiceException;

    Optional<Department> findDepartment(long id) throws ServiceException;

    void updateDepartment(Department department, String servletContextPath, Part imageFile)
            throws ServiceException;
}
