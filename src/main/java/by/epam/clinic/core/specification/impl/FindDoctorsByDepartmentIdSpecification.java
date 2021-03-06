package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindDoctorsByDepartmentIdSpecification implements Specification {
    private long departmentId;

    private final String SQL_QUERY = "SELECT id, name, surname, lastname," +
            " user_id, specialization, category, " +
            "department_id, image_path FROM doctors WHERE department_id = '%s'";

    public FindDoctorsByDepartmentIdSpecification(long departmentId) {
        this.departmentId = departmentId;
    }



    @Override
    public String toSqlQuery() {
        return String.format(SQL_QUERY, departmentId);
    }
}
