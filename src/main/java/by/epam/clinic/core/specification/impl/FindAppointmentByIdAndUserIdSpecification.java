package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindAppointmentByIdAndUserIdSpecification implements Specification {
    private long userId;

    private long appointmentId;

    public FindAppointmentByIdAndUserIdSpecification(long appointmentId, long userId) {
        this.userId = userId;
        this.appointmentId = appointmentId;
    }

    private static final String SQL_QUERY =
            "SELECT appointments.id, doctor_id, customer_id, date_time," +
                    " purpose FROM appointments INNER JOIN doctors" +
                    " ON appointments.doctor_id = doctors.id WHERE" +
                    " doctors.user_id = '%s' AND appointments.id = '%s'";

    @Override
    public String toSqlQuery() {
       return String.format(SQL_QUERY,userId,appointmentId);
    }
}
