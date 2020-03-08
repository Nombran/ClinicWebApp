package by.epam.clinic.core.pool;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

 class ConnectionProvider {
    private final static Logger logger = LogManager.getLogger();

    private final static String DB_PROPERTIES_PATH = "property.database";

    private final static String URL_PROPERTY = "url";

    private final static String JDBC_DRIVER = "org.postgresql.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
           logger.log(Level.FATAL, "Error in loading JDBC Driver",e);
           throw new ExceptionInInitializerError(String.format("JDBC Driver not found because of %s",e));
        }
    }

     static Connection getConnection() throws SQLException, ConnectionProviderException {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(DB_PROPERTIES_PATH);
            Properties properties = new Properties();
            resource.keySet().forEach(key -> properties.put(key, resource.getString(key)));
            String url = properties.getProperty(URL_PROPERTY);
            Connection connection = DriverManager.getConnection(url, properties);
            return new ProxyConnection(connection);
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Cannot find resource bundle with name "+ DB_PROPERTIES_PATH, e);
            throw new ConnectionProviderException("Cannot find resource bundle with name "
                    + DB_PROPERTIES_PATH,e);
        }
    }

}
