package by.epam.clinic.core.pool;

import by.epam.clinic.core.repository.AbstractRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private Connection connection;

    public void init() throws TransactionManagerException { //init
        if(connection == null) {
            connection = ConnectionPool.INSTANCE.getConnection();
        } else {
            throw new TransactionManagerException("Connection is already initialized");
        }
    }

    public void beginTransaction() throws TransactionManagerException {
        if(connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new TransactionManagerException("Error when set auto commit to false", e);
            }
        } else {
            throw new TransactionManagerException("Connection is not initialized");
        }
    }

    public void rollbackTransaction() throws TransactionManagerException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new TransactionManagerException("Error in rollback transaction", e);
            }
        } else {
            throw new TransactionManagerException("Connection is not initialized");
        }
    }

    public void commitTransaction() throws TransactionManagerException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new TransactionManagerException("Error in commit transaction", e);
            }
        } else {
            throw new TransactionManagerException("Connection is not initialized");
        }
    }

    public void releaseResources() throws TransactionManagerException {
        if(connection != null) {
            try {
                if(!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                ConnectionPool.INSTANCE.releaseConnection(connection);
                connection = null;
            } catch (ConnectionPoolException e) {
                throw new TransactionManagerException("Error in releasing connection to connection pool");
            } catch (SQLException e) {
                throw new TransactionManagerException("Error when set auto commit to true", e);
            }
        } else {
            throw new TransactionManagerException("Connection is not initialized");
        }
    }

    public void setConnectionToRepository(AbstractRepository repository, AbstractRepository ... repositories) throws TransactionManagerException {
        if(connection != null) {
            repository.setConnection(connection);
            if (repositories != null) {
                for (AbstractRepository repo : repositories) {
                    repo.setConnection(connection);
                }
            }
        } else {
            throw new TransactionManagerException("Connection is not initialized");
        }
    }
}
