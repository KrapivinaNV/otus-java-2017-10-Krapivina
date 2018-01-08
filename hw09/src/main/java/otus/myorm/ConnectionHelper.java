package otus.myorm;


import org.h2.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());

            String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;" +
                    "user=sa;" +              //login
                    "password=sa";        //password

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

