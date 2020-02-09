package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.model.LocalDateTimeConverter;
import by.epam.clinic.core.specification.Specification;

import java.time.LocalDateTime;

public class FindFreeDoctorAppointmentsByDoctorIdSpecification implements Specification {
    private long doctorId;

    private static final String SQL_QUERY = "SELECT appointments.id, doctor_id, customer_id, date_time," +
            " purpose FROM appointments WHERE" +
            " doctor_id = '%s' AND customer_id IS NULL AND date_time > '%s' ORDER BY date_time";

    public FindFreeDoctorAppointmentsByDoctorIdSpecification(long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toSqlQuery() {
        LocalDateTime dateTime = LocalDateTime.now();
        long millis = LocalDateTimeConverter.toMilliseconds(dateTime);
        return String.format(SQL_QUERY,doctorId,millis);
    }
}
