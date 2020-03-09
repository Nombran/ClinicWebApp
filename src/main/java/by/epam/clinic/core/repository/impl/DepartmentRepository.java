package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.model.DepartmentAttribute;
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

    private static final String REMOVE_SQL =
            "DELETE FROM departments WHERE id=?";


    @Override
    public void add(Department item) throws RepositoryException {
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
    public int update(Department item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            fillStatement(preparedStatement, item);
            preparedStatement.setLong(5,item.getId());
            return preparedStatement.executeUpdate();
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
    public List<Department> query(Specification specification) throws RepositoryException {
        List<Department> result = new ArrayList<>();
        String sqlQuery = specification.toSqlQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(DepartmentAttribute.ID_ATTR);
                    String name = resultSet.getString(DepartmentAttribute.NAME_ATTR);
                    String description = resultSet.getString(DepartmentAttribute.DESCRIPTION_ATTR);
                    String phone = resultSet.getString(DepartmentAttribute.PHONE_ATTR);
                    String imagePath = resultSet.getString(DepartmentAttribute.IMAGE_PATH_ATTR);
                    Department department = new Department(name, description, phone, imagePath);
                    department.setId(id);
                    result.add(department);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }

    private void fillStatement(PreparedStatement preparedStatement, Department item) throws SQLException {
        preparedStatement.setString(1,item.getName());
        preparedStatement.setString(2,item.getDescription());
        preparedStatement.setString(3,item.getPhone());
        preparedStatement.setString(4,item.getImagePath());
    }
}
