package by.epam.clinic.core.specification.impl;

import by.epam.clinic.core.specification.Specification;

public class FindUserByDoctorIdSpeification implements Specification {
    private long doctorId;

    private final static String SQL_QUERY = "SELECT users.id, login," +
            " password, email, role, status FROM users INNER JOIN doctors" +
            " ON users.id = doctors.user_id WHERE doctors.id = ";

    public FindUserByDoctorIdSpeification(long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toSqlQuery() {
        return SQL_QUERY + doctorId;
    }
}
