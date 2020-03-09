package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindUserByLoginSpecification implements Specification {
    private String login;

    private static final String SQL_QUERY =
            "SELECT id, login, password, email, role," +
                    " status FROM users WHERE login = '%s'";


    public FindUserByLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public String toSqlQuery() {
        return String.format(SQL_QUERY, login);
    }
}
