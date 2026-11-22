
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // Preserve the existing local XAMPP MySQL configuration.
        if (url == null || url.isBlank()) {
            url = "jdbc:mysql://localhost:3306/yapspace_db";
            user = "root";
            password = "";
        }

        if (user == null) {
            user = "";
        }

        if (password == null) {
            password = "";
        }

        try {

            // Select the appropriate JDBC driver.
            if (url.startsWith("jdbc:postgresql:")) {
                Class.forName("org.postgresql.Driver");

            } else if (url.startsWith("jdbc:mysql:")) {
                Class.forName("com.mysql.cj.jdbc.Driver");

            } else {
                throw new IllegalArgumentException(
                        "Unsupported database URL.");
            }

            Connection connection =
                    DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully!");

            return connection;

        } catch (ClassNotFoundException | SQLException
                 | IllegalArgumentException e) {

            System.err.println("Database connection failed!");
            System.err.println(e.getMessage());

            return null;
        }
    }
}
