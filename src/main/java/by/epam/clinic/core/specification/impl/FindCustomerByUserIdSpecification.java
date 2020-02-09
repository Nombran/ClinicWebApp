package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindCustomerByUserIdSpecification implements Specification {
    private long userId;

    private static final String SQL_QUERY = "SELECT id, name," +
            " surname, lastname, birthday, phone," +
            " user_id FROM customers WHERE user_id = ";

    public FindCustomerByUserIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + userId;
    }
}
