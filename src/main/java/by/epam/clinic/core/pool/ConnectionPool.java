package by.epam.clinic.core.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private final Logger logger = LogManager.getLogger();

    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;

    private final static int DEFAULT_POOL_SIZE = 32;

    ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionCreator.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException e) {
                logger.error("Error in getting connection", e);
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Error in taking connection from pool",e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection.getClass() == ProxyConnection.class) {
            givenAwayConnections.remove(connection);
            freeConnections.offer(connection);
        } else {
            throw new ConnectionPoolException("Not a ProxyConnection class");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = (ProxyConnection) freeConnections.take();
                connection.reallyClose();
            } catch (InterruptedException e) {
               logger.error("Error in destroying connection pool",e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
               logger.error("Error in deregister drivers process",e);
            }
        }
    }
}

