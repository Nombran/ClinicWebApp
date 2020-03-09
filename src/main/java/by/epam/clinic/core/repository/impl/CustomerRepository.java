package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.CustomerAttribute;
import by.epam.clinic.core.repository.AbstractRepository;
import by.epam.clinic.core.specification.Specification;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository extends AbstractRepository<Customer> {
    private static final String INSERT_SQL =
            "INSERT INTO customers(name, surname, lastname, birthday, phone," +
                    "user_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE customers SET name=?, surname=?, lastname=?, birthday=?," +
                    " phone=?, user_id=? WHERE id=?";

    private static final String REMOVE_SQL =
            "DELETE FROM customers WHERE id=?";


    @Override
    public void add(Customer item) throws RepositoryException {
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
            throw new RepositoryException("Error in creating customer",e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public int update(Customer item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            fillStatement(preparedStatement, item);
            preparedStatement.setLong(7,item.getId());
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
    public List<Customer> query(Specification specification) throws RepositoryException {
        List<Customer> result = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            String sqlQuery = specification.toSqlQuery();
            statement = connection.prepareStatement(sqlQuery);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(CustomerAttribute.ID_ATTR);
                    String name = resultSet.getString(CustomerAttribute.NAME_ATTR);
                    String surname = resultSet.getString(CustomerAttribute.SURNAME_ATTR);
                    String lastname = resultSet.getString(CustomerAttribute.LASTNAME_ATTR);
                    long birthdayFromDb = resultSet.getLong(CustomerAttribute.BIRTHDAY);
                    LocalDate birthday = LocalDate.ofEpochDay(birthdayFromDb);
                    String phone = resultSet.getString(CustomerAttribute.PHONE);
                    long userId = resultSet.getLong(CustomerAttribute.USER_ID);
                    Customer customer = new Customer(name, surname, lastname, birthday, phone);
                    customer.setUserId(userId);
                    customer.setId(id);
                    result.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }

    private void fillStatement(PreparedStatement statement, Customer item) throws SQLException {
        statement.setString(1,item.getName());
        statement.setString(2,item.getSurname());
        statement.setString(3,item.getLastname());
        statement.setLong(4,item.getBirthday().toEpochDay());
        statement.setString(5,item.getPhone());
        statement.setLong(6,item.getUserId());
    }
}
