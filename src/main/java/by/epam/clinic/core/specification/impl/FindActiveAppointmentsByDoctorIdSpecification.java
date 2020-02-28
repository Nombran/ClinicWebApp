package by.epam.clinic.core.specification.impl;

import by.epam.clinic.util.LocalDateTimeConverter;
import by.epam.clinic.core.specification.Specification;

import java.time.LocalDateTime;

public class FindActiveAppointmentsByDoctorIdSpecification implements Specification {
    private long doctorId;

    private static final String SQL_QUERY =
            "SELECT id, doctor_id, customer_id, date_time," +
                    " purpose FROM appointments WHERE date_time > '%s' AND doctor_id = '%s'";

    public FindActiveAppointmentsByDoctorIdSpecification(long doctorId){
        this.doctorId = doctorId;
    }

    @Override
    public String toSqlQuery() {
        LocalDateTime dateTime = LocalDateTime.now();
        long millis = LocalDateTimeConverter.toMilliseconds(dateTime);
        return String.format(SQL_QUERY,millis,doctorId);
    }
}
