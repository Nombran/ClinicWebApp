package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindDepartmentByIdSpecification implements Specification {
    private long id;

    private static final String SQL_QUERY =
            "SELECT id, name, description, phone, image_path FROM departments WHERE id = ";

    public FindDepartmentByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + id;
    }
}
