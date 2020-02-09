package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindCustomerByIdSpecification implements Specification {
    private long id;

    private static final String SQL_QUERY = "SELECT id, name," +
            " surname, lastname, birthday, phone, social_status," +
            " user_id FROM customers WHERE id = ";

    public FindCustomerByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + id;
    }
}
