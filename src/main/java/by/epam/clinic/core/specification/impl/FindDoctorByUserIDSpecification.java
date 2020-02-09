package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindDoctorByUserIDSpecification implements Specification {
    private long userId;

    private static final String SQL_QUERY =
            "SELECT id, name, surname, lastname," +
                    " user_id, specialization, category, " +
                    "department_id, image_path FROM doctors WHERE user_id = '%s'";

    public FindDoctorByUserIDSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format(SQL_QUERY,userId);
    }
}
