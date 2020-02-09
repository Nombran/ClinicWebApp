package by.epam.clinic.core.pool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionCreator {

    private final static String DB_PROPERTIES_PATH = "property.database";

    private final static String URL_PROPERTY = "url";

    private final static String JDBC_DRIVER = "org.postgresql.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
           //log
        }
    }

    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle(DB_PROPERTIES_PATH);
        Properties properties = new Properties();
        resource.keySet().forEach(key -> properties.put(key,resource.getString(key)));
        String url = properties.getProperty(URL_PROPERTY);
        return DriverManager.getConnection(url,properties);
    }

}
