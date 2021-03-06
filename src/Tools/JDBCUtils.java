package Tools;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static BasicDataSource dataSource = new BasicDataSource();
    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;
    private static Integer maxTotal;
    private static Integer maxIdle;
    private static Integer minIdle;
    private static Integer initialSize;

    static {
        try {
            readProperty(); //reads database.properties file under src directory
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(url);

            // If you are not using root to log on MySQL, you need to change here.
            dataSource.setUsername(username);

            // you need to change this to your DB password
            dataSource.setPassword(password);

            // The maximum number of active connections that can be allocated from this pool
            // at the same time, or non-positive for no limit.
            dataSource.setMaxTotal(maxTotal);

            // The maximum number of connections that can remain idle in the pool, without
            // extra ones being released, or negative for no limit.
            dataSource.setMaxIdle(maxIdle);

            // The minimum number of active connections that can remain idle in the pool,
            // without extra ones being created, or 0 to create none.
            dataSource.setMinIdle(minIdle);

            // Sets the initial size of the connection pool.
            dataSource.setInitialSize(initialSize);
        } catch (Exception ex) {
            throw new RuntimeException(ex + " Database connection failure");
        }
    }

    // get a datasource
    public static DataSource getDataSource() {
        return dataSource;
    }

    //Returns connection
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static void readProperty() throws IOException {
        // Load a properties file from classpath
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("database.properties");
        Properties prop = new Properties();
        prop.load(is);
        driverClass = prop.getProperty("driverClass");
        url = prop.getProperty("url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        maxTotal = Integer.parseInt(prop.getProperty("maxTotal"));
        maxIdle = Integer.parseInt(prop.getProperty("maxIdle"));
        minIdle = Integer.parseInt(prop.getProperty("minIdle"));
        initialSize = Integer.parseInt(prop.getProperty("initialSize"));
    }
}
