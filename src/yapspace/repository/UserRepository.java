package yapspace.repository;

import database.DatabaseConnection;
import yapspace.model.AdminUser;
import yapspace.model.RegularUser;
import yapspace.model.Role;
import yapspace.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRepository {

    public int save(String username, String email,
                    String passwordHash, String salt, Role role) {

        String sql = "INSERT INTO users "
                + "(username, email, password, salt, role) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, passwordHash);
                stmt.setString(4, salt);
                stmt.setString(5, role.name());

                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }

            throw new SQLException("Could not retrieve user ID.");

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save user.", e);
        }
    }

    public User findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {

                        int id = rs.getInt("user_id");
                        String name = rs.getString("username");
                        String email = rs.getString("email");
                        String passwordHash = rs.getString("password");
                        String salt = rs.getString("salt");

                        Role role = Role.valueOf(rs.getString("role"));

                        Timestamp timestamp = rs.getTimestamp("created_at");
                        LocalDateTime createdAt =
                                timestamp.toLocalDateTime();

                        if (role == Role.ADMIN) {
                            return new AdminUser(
                                    id, name, email, salt,
                                    passwordHash, createdAt);
                        }

                        return new RegularUser(
                                id, name, email, salt,
                                passwordHash, createdAt);
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find user.", e);
        }
    }

    public boolean existsByEmail(String email) {

        String sql = "SELECT user_id FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to check email.", e);
        }
    }
public User findById(int id) {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        int userId = rs.getInt("user_id");
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        String passwordHash = rs.getString("password");
                        String salt = rs.getString("salt");
                        Role role = Role.valueOf(rs.getString("role"));

                        LocalDateTime createdAt =
                                rs.getTimestamp("created_at").toLocalDateTime();

                        if (role == Role.ADMIN) {
                            return new AdminUser(userId, username, email,
                                    salt, passwordHash, createdAt);
                        }

                        return new RegularUser(userId, username, email,
                                salt, passwordHash, createdAt);
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find user by ID.", e);
        }
    }
}