
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // Use XAMPP settings when running locally
        if (url == null || url.isBlank()) {
            url = "jdbc:mysql://localhost:3306/yapspace_db";
            user = "root";
            password = "";
        }

        try {
            Connection connection =
                    DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully!");
            return connection;

        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
