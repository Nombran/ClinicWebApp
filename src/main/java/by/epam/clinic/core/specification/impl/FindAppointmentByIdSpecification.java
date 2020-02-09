package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindAppointmentByIdSpecification implements Specification {
    private long appointmentId;

    private static final String SQL_QUERY = "SELECT id, doctor_id, customer_id, date_time," +
            " purpose FROM appointments WHERE id = ";

    public FindAppointmentByIdSpecification(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + appointmentId;
    }
}
