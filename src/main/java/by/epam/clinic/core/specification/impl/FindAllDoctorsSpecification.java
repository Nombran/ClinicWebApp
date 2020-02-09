package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindAllDoctorsSpecification implements Specification {
    private static final String SQL_QUERY = "SELECT id, name, surname," +
            " lastname, user_id, specialization, category, department_id, image_path FROM doctors";

    @Override
    public String toSqlQuery() {
        return SQL_QUERY;
    }
}
