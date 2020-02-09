package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.model.LocalDateTimeConverter;
import by.epam.clinic.core.specification.Specification;

import java.time.LocalDateTime;

public class FindCustomersByDoctorUserIdSpecification implements Specification {
    private long userId;

    private static final String SQL_QUERY = "SELECT customers.id, customers.name," +
            " customers.surname, customers.lastname, birthday, phone, customers.user_id FROM" +
            " customers INNER JOIN appointments ON customers.id = appointments.customer_id" +
            " INNER JOIN doctors ON doctors.id = appointments.doctor_id WHERE doctors.user_id" +
            " = '%s' AND appointments.date_time > '%s'";


    public FindCustomersByDoctorUserIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        long millis = LocalDateTimeConverter.toMilliseconds(LocalDateTime.now());
        return String.format(SQL_QUERY,userId,millis);
    }
}
