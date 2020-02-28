package by.epam.clinic.core.repository;

import by.epam.clinic.core.pool.ConnectionPool;
import by.epam.clinic.core.repository.impl.RepositoryException;
import by.epam.clinic.core.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * Generic Abstract class is a realisation of Repository pattern.
 * Represents CRUD methods to data base access.
 *
 * @param <T> represents entity from table in database
 */
public abstract class AbstractRepository<T> {
    /**
     * The Connection, taken from {@link ConnectionPool}.
     */
    protected Connection connection;

    /**
     * Method adds data to the database table.
     *
     * @param item represents entity to add.
     * @throws RepositoryException if database problem occurs.
     */
    public abstract void add(T item) throws RepositoryException;

    /**
     * Method updates data in database table.
     *
     * @param item represents entity to update.
     * @throws RepositoryException if database problem occurs.
     */
    public abstract void update(T item) throws RepositoryException;

    /**
     * Method executes sql query.
     *
     * @param specification contains sql query to execute
     * @return the list of entity objects, as result of sql query.
     * @throws RepositoryException if database problem occurs.
     */
    public abstract List<T> query(Specification specification) throws RepositoryException;

    /**
     * Method closes statement.
     *
     * @param statement used during one of database operations.
     */
    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    /**
     * Method sets connection.
     *
     * @param connection is an database connection object.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method removes data from database table.
     *
     * @param id        represents primary key from database table.
     * @param removeSql represents sql query as string.
     * @return the number of deleted rows.
     * @throws RepositoryException if database problem occurs.
     */
    protected int remove(long id, String removeSql) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(removeSql);
            preparedStatement.setLong(1,id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Error in removing appointment",e);
        } finally {
            close(preparedStatement);
        }
    }
}
