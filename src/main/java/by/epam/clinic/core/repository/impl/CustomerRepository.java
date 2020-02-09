package by.epam.clinic.core.repository.impl;

import by.epam.clinic.core.model.Customer;
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

    @SuppressWarnings("Duplicates")
    @Override
    public void add(Customer item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,item.getName());
            preparedStatement.setString(2,item.getSurname());
            preparedStatement.setString(3,item.getLastname());
            preparedStatement.setLong(4,item.getBirthday().toEpochDay());
            preparedStatement.setString(5,item.getPhone());
            preparedStatement.setLong(6,item.getUserId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    item.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(preparedStatement);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void update(Customer item) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1,item.getName());
            preparedStatement.setString(2,item.getSurname());
            preparedStatement.setString(3,item.getLastname());
            preparedStatement.setLong(4,item.getBirthday().toEpochDay());
            preparedStatement.setString(5,item.getPhone());
            preparedStatement.setLong(6,item.getUserId());
            preparedStatement.setLong(7,item.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Error in updating item",e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public List<Customer> query(Specification specification) throws RepositoryException {
        List<Customer> result = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            String sqlQuery = specification.toSqlQuery();
            statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String lastname = resultSet.getString("lastname");
                long birthdayFromDb = resultSet.getLong("birthday");
                LocalDate birthday = LocalDate.ofEpochDay(birthdayFromDb);
                String phone = resultSet.getString("phone");
                long userId = resultSet.getLong("user_id");
                Customer customer = new Customer(name,surname,lastname,birthday,phone);
                customer.setUserId(userId);
                customer.setId(id);
                result.add(customer);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error in query",e);
        } finally {
            close(statement);
        }
        return result;
    }

}
