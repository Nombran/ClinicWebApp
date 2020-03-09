package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.AppointmentAttribute;
import by.epam.clinic.core.repository.AbstractRepository;
import by.epam.clinic.core.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository extends AbstractRepository<Appointment> {
    private static final String INSERT_SQL =
            "INSERT INTO appointments(doctor_id, customer_id, date_time, purpose) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE appointments SET doctor_id=?, customer_id=?, date_time=?, purpose=? WHERE id=?";

    private static final String REMOVE_SQL =
            "DELETE FROM appointments WHERE id=?";

    private static final String UPDATE_CONDITION = " AND customer_id IS NULL";

    @Override
    public void add(Appointment item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,item.getDoctorId());
            long customerId = item.getCustomerId();
            if(customerId == 0) {
                preparedStatement.setObject(2,null);
            } else {
                preparedStatement.setLong(2, customerId);
            }
            LocalDateTime dateTime = item.getDateTime();
            ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault());
            long millis = zdt.toInstant().toEpochMilli();
            preparedStatement.setLong(3,millis);
            preparedStatement.setString(4,item.getPurpose());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    item.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in creating appointment", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public int update(Appointment item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        String SQL_QUERY = UPDATE_SQL;
        if(item.getCustomerId() != 0) {
            SQL_QUERY = UPDATE_SQL + UPDATE_CONDITION;
        }
        try {
            preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setLong(1,item.getDoctorId());
            long customerId = item.getCustomerId();
            if(customerId == 0) {
                preparedStatement.setObject(2,null);
            } else {
                preparedStatement.setLong(2, item.getCustomerId());
            }
            LocalDateTime dateTime = item.getDateTime();
            ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            long milli = instant.toEpochMilli();
            preparedStatement.setLong(3,milli);
            preparedStatement.setString(4,item.getPurpose());
            preparedStatement.setLong(5,item.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
           throw new RepositoryException("Error in updating appointment", e);
        } finally {
            close(preparedStatement);
        }
    }

    public int remove(long id) throws RepositoryException {
        return super.remove(id,REMOVE_SQL);
    }

    @Override
    public List<Appointment> query(Specification specification) throws RepositoryException {
        List<Appointment> result = new ArrayList<>();
        String sqlQuery = specification.toSqlQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(AppointmentAttribute.ID_ATTR);
                    long doctorId = resultSet.getLong(AppointmentAttribute.DOCTOR_ID_ATTR);
                    long customerId = resultSet.getLong(AppointmentAttribute.CUSTOMER_ID_ATTR);
                    long milli = resultSet.getLong(AppointmentAttribute.DATE_TIME_ATTR);
                    Instant instant = Instant.ofEpochMilli(milli);
                    ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
                    LocalDateTime dateTime = zonedDateTime.toLocalDateTime();
                    String purpose = resultSet.getString(AppointmentAttribute.PURPOSE_ATTR);
                    Appointment appointment = new Appointment(doctorId, customerId, purpose, dateTime);
                    appointment.setId(id);
                    result.add(appointment);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }

}
