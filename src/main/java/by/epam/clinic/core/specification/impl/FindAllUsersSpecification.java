package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindAllUsersSpecification implements Specification {
    private static final String SQL_QUERY =
            "SELECT id, login, password, email, role, status FROM users";

    @Override
    public String toSqlQuery() {
        return SQL_QUERY;
    }
}
