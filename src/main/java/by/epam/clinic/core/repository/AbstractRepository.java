package by.epam.clinic.core.repository;

import by.epam.clinic.core.repository.impl.RepositoryException;
import by.epam.clinic.core.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractRepository<T> {
    protected Connection connection;

    private static final String REMOVE_SQL =
            "DELETE FROM appointments WHERE id=?";

    public abstract void add(T item) throws RepositoryException;
    public abstract void update(T item) throws RepositoryException;
    public abstract List<T> query(Specification specification) throws RepositoryException;
    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void remove(long id) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(REMOVE_SQL);
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Error in removing appointment",e);
        } finally {
            close(preparedStatement);
        }
    }
}
