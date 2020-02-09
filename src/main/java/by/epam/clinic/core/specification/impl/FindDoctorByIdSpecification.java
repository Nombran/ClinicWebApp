package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindDoctorByIdSpecification implements Specification {
    private long id;

    private final static String SQL_QUERY = "SELECT id, name, surname," +
            " lastname, user_id, specialization, category, department_id," +
            " image_path FROM doctors WHERE id = ";

    public FindDoctorByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + id;
    }
}
