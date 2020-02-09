package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.model.LocalDateTimeConverter;
import by.epam.clinic.core.specification.Specification;

import java.time.LocalDateTime;

public class FindActiveAppointmentsByCustomerIdSpecification implements Specification {
    private long customerId;

    private static final String SQL_QUERY =
            "SELECT id, doctor_id, customer_id, date_time," +
                    " purpose FROM appointments WHERE date_time > '%s' AND customer_id = '%s'";

    public FindActiveAppointmentsByCustomerIdSpecification(long customerId){
        this.customerId = customerId;
    }

    @Override
    public String toSqlQuery() {
        LocalDateTime dateTime = LocalDateTime.now();
        long millis = LocalDateTimeConverter.toMilliseconds(dateTime);
        return String.format(SQL_QUERY,millis,customerId);
    }
}
