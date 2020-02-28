package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FindAppointmentByDateTimeSpecification implements Specification {
    private LocalDateTime dateTime;

    private long doctor_id;

    private static final long MIN_APPOINTMENT_DURATION_IN_MILLI = 600000;

    private static final String SQL_QUERY =
            "SELECT id, doctor_id, customer_id, date_time," +
                    " purpose FROM appointments WHERE date_time BETWEEN '%s' AND '%s' AND doctor_id = '%s'";

    public FindAppointmentByDateTimeSpecification(LocalDateTime dateTime, long doctor_id) {
        this.dateTime = dateTime;
        this.doctor_id = doctor_id;
    }

    @Override
    public String toSqlQuery() {
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        long milli = instant.toEpochMilli();
        return String.format(SQL_QUERY, milli-MIN_APPOINTMENT_DURATION_IN_MILLI,milli
                + MIN_APPOINTMENT_DURATION_IN_MILLI, doctor_id);
    }
}
