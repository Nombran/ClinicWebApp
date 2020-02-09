package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindUserByIdSpecification implements Specification {
    private long id;

    private static final String SQL_QUERY = "SELECT id, login, password, email, role, status FROM users WHERE id=";

    public FindUserByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + id;
    }
}
