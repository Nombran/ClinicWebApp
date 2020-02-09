package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.repository.AbstractRepository;
import by.epam.clinic.core.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository extends AbstractRepository<Department> {
    private static final String INSERT_SQL = "INSERT INTO " +
            "departments(name, description, phone, image_path) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_SQL = "UPDATE departments " +
            "SET name=?, description=?, phone=?, image_path=? WHERE id=?";

    @SuppressWarnings("Duplicates")
    @Override
    public void add(Department item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,item.getName());
            preparedStatement.setString(2,item.getDescription());
            preparedStatement.setString(3,item.getPhone());
            preparedStatement.setString(4,item.getImagePath());
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

    @SuppressWarnings("Duplicates")
    @Override
    public void update(Department item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1,item.getName());
            preparedStatement.setString(2,item.getDescription());
            preparedStatement.setString(3,item.getPhone());
            preparedStatement.setString(4,item.getImagePath());
            preparedStatement.setLong(5,item.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Error in updating item",e);
        } finally {
            close(preparedStatement);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Department> query(Specification specification) throws RepositoryException {
        List<Department> result = new ArrayList<>();
        String sqlQuery = specification.toSqlQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String phone = resultSet.getString("phone");
                String imagePath = resultSet.getString("image_path");
                Department department = new Department(name, description, phone, imagePath);
                department.setId(id);
                result.add(department);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }
}
