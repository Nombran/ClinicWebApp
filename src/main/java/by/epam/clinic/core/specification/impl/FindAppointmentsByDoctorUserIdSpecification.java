package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.model.LocalDateTimeConverter;
import by.epam.clinic.core.specification.Specification;

import java.time.LocalDateTime;


public class FindAppointmentsByDoctorUserIdSpecification implements Specification {
    private long userId;

    private static final String SQL_QUERY =
            "SELECT appointments.id, doctor_id, customer_id, date_time," +
                    " purpose FROM appointments INNER JOIN doctors" +
                    " ON appointments.doctor_id = doctors.id WHERE" +
                    " doctors.user_id = '%s' AND date_time > '%s' AND appointments.customer_id IS NOT NULL";

    public FindAppointmentsByDoctorUserIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        long millis =  LocalDateTimeConverter.toMilliseconds(LocalDateTime.now());
        return String.format(SQL_QUERY, userId,millis);
    }
}
