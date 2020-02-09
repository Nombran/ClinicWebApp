package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindDoctorsByCustomerIdSpecification implements Specification {
    private long customerId;

    private final static String SQL_QUERY = "SELECT DISTINCT doctors.id, doctors.name," +
            " doctors.surname, doctors.lastname, doctors.user_id, specialization, category," +
            " department_id, image_path FROM doctors INNER JOIN appointments ON" +
            " doctors.id=appointments.doctor_id INNER JOIN customers ON" +
            " appointments.customer_id=customers.id WHERE customers.id = ";

   public FindDoctorsByCustomerIdSpecification(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + customerId;
    }
}
