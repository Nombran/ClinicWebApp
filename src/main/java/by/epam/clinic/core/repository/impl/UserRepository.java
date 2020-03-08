package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.User;
import by.epam.clinic.core.model.UserAttribute;
import by.epam.clinic.core.model.UserRole;
import by.epam.clinic.core.model.UserSatus;
import by.epam.clinic.core.repository.AbstractRepository;
import by.epam.clinic.core.specification.Specification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository<User> {
    private static final String INSERT_SQL =
            "INSERT INTO users(login,password,email,role,status) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE users SET login=?, password=?, email=?, role=?, status=? WHERE id=?";

    private static final String REMOVE_SQL =
            "DELETE FROM users WHERE id=?";


    @Override
    public void add(User item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement(INSERT_SQL,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,item.getLogin());
            preparedStatement.setString(2,item.getPassword());
            preparedStatement.setString(3,item.getEmail());
            preparedStatement.setString(4,item.getRole().toString());
            preparedStatement.setString(5,item.getStatus().toString());
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
    public int update(User item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1,item.getLogin());
            preparedStatement.setString(2,item.getPassword());
            preparedStatement.setString(3,item.getEmail());
            preparedStatement.setString(4,item.getRole().toString());
            preparedStatement.setString(5,item.getStatus().toString());
            preparedStatement.setLong(6,item.getId());
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
    public List<User> query(Specification specification) throws RepositoryException {
        List<User> result = new ArrayList<>();
        String sqlQuery = specification.toSqlQuery();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                long id = resultSet.getLong(UserAttribute.ID_ATTR);
                String login = resultSet.getString(UserAttribute.LOGIN_ATTR);
                String password = resultSet.getString(UserAttribute.PASSWORD_ATTR);
                String email = resultSet.getString(UserAttribute.EMAIL_ATTR);
                UserRole userRole = UserRole.valueOf(resultSet.getString(UserAttribute.ROLE_ATTR));
                UserSatus userSatus = UserSatus.valueOf(resultSet.getString(UserAttribute.STATUS_ATTR));
                User user = new User(login,password,email,userRole,userSatus);
                user.setId(id);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }

}
