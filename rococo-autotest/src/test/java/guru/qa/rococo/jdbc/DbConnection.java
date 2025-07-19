package guru.qa.rococo.jdbc;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DbConnection {
    private static final Map<String, DataSource> dataSource = new ConcurrentHashMap<>();

    private DbConnection(){

    }
    public static DataSource dataSource(String jdbcUrl, String userName, String password){
        return dataSource.computeIfAbsent(
                jdbcUrl,
                key -> {
                    PGSimpleDataSource ds = new PGSimpleDataSource();
                    ds.setUser(userName);
                    ds.setPassword(password);
                    ds.setUrl(key);
                    return ds;
                }
        );
    }

    public static Connection connection(String jdbcUrl,String userName,String password) throws SQLException {
        return dataSource(jdbcUrl,userName,password).getConnection();
    }


}
