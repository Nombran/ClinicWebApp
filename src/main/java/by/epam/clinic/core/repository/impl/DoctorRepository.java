package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.repository.AbstractRepository;
import by.epam.clinic.core.specification.Specification;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository extends AbstractRepository<Doctor> {

    private static final String INSERT_SQL = "INSERT INTO doctors(name," +
            " surname, lastname, user_id, specialization, category, department_id," +
            " image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL = "UPDATE doctors SET name=?, surname=?, " +
            "lastname=?, user_id=?, specialization=?, category=?, department_id=?, " +
            "image_path=? WHERE id=?";

    private static final String REMOVE_SQL =
            "DELETE FROM doctors WHERE id=?";



    @Override
    public void add(Doctor item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            fillStatement(preparedStatement, item);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    item.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in creating item",e);
        } finally {
            close(preparedStatement);
        }
    }


    @Override
    public void update(Doctor item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            fillStatement(preparedStatement,item);
            preparedStatement.setLong(9,item.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Error in updating item",e);
        } finally {
            close(preparedStatement);
        }
    }

    public int remove(long id) throws RepositoryException {
        return super.remove(id,REMOVE_SQL);
    }

    @Override
    public List<Doctor> query(Specification specification) throws RepositoryException {
        List<Doctor> result = new ArrayList<>();
        String sqlQuery = specification.toSqlQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String surname = resultSet.getString("surname");
                long userId = resultSet.getLong("user_id");
                String specialization = resultSet.getString("specialization");
                String category = resultSet.getString("category");
                long departmentId = resultSet.getLong("department_id");
                String imagePath = resultSet.getString("image_path");
                Doctor doctor = new Doctor(id, name, surname, lastname, specialization,
                        category, userId, departmentId, imagePath);
                result.add(doctor);
            }
        } catch (SQLException e) {
           throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }

    private void fillStatement(PreparedStatement statement, Doctor item) throws SQLException {
        statement.setString(1,item.getName());
        statement.setString(2,item.getSurname());
        statement.setString(3,item.getLastname());
        statement.setLong(4,item.getUserId());
        statement.setString(5,item.getSpecialization());
        statement.setString(6,item.getCategory());
        statement.setLong(7,item.getDepartmentId());
        statement.setString(8,item.getImagePath());
    }
}
