package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindUserByLoginSpecification implements Specification {
    private String login;

    private String password;

    private static final String SQL_QUERY =
            "SELECT id, login, password, email, role, status FROM users WHERE login = '%s' AND password = '%s'";


    public FindUserByLoginSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toSqlQuery() {
        return String.format(SQL_QUERY,login,password);
    }
}
