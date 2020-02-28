package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.service.impl.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

/**
 * Interface represents department's processing logic
 */
public interface DepartmentService {

    /**
     * Method defines logic of creating new department.
     *
     * @param name               the name of department.
     * @param description        the description of department.
     * @param phone              the phone of department.
     * @param servletContextPath the servlet context path.
     * @param imageFile          the image file.
     * @throws ServiceException if an error occurs while creating department.
     */
    void createDepartment(String name, String description, String phone,
                             String servletContextPath, Part imageFile) throws ServiceException;

    /**
     * Method defines logic of getting all departments as list.
     *
     * @return list of all departments.
     * @throws ServiceException if error occurs while getting all departments.
     */
    List<Department> getAllDepartments() throws ServiceException;

    /**
     * Method defines logic of getting searching department by it's primary key.
     *
     * @param id represents primary key in departments database table.
     * @return the optional, that can contain department.
     * @throws ServiceException if an error occurs while searching a department.
     */
    Optional<Department> findDepartment(long id) throws ServiceException;

    void updateDepartment(Department department, String servletContextPath, Part imageFile)
            throws ServiceException;
}
