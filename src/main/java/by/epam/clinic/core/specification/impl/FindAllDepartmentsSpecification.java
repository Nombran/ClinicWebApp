package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindAllDepartmentsSpecification implements Specification {
    private static final String SQL_QUERY =
            "SELECT id, name, description, phone, image_path FROM departments";

    @Override
    public String toSqlQuery() {
        return SQL_QUERY;
    }
}
