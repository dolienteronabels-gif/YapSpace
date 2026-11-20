package yapspace.repository;

import database.DatabaseConnection;
import yapspace.model.Reaction;
import yapspace.model.ReactionType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReactionRepository {

    public void save(int postId, String username, ReactionType type) {

        String sql = "INSERT INTO reactions "
                + "(post_id, user_id, reaction_type) "
                + "VALUES (?, (SELECT user_id FROM users WHERE username = ?), ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setString(2, username);
                stmt.setString(3, type.name());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save reaction.", e);
        }
    }

    public void update(int postId, String username, ReactionType type) {

        String sql = "UPDATE reactions "
                + "SET reaction_type = ? "
                + "WHERE post_id = ? "
                + "AND user_id = (SELECT user_id FROM users WHERE username = ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, type.name());
                stmt.setInt(2, postId);
                stmt.setString(3, username);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to update reaction.", e);
        }
    }

    public void delete(int postId, String username) {

        String sql = "DELETE FROM reactions "
                + "WHERE post_id = ? "
                + "AND user_id = (SELECT user_id FROM users WHERE username = ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setString(2, username);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete reaction.", e);
        }
    }

    public Reaction findByUser(int postId, String username) {

        String sql = "SELECT r.*, u.username "
                + "FROM reactions r "
                + "JOIN users u ON r.user_id = u.user_id "
                + "WHERE r.post_id = ? AND u.username = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setString(2, username);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapReaction(rs);
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find reaction.", e);
        }
    }

    public List<Reaction> findByPost(int postId) {

        String sql = "SELECT r.*, u.username "
                + "FROM reactions r "
                + "JOIN users u ON r.user_id = u.user_id "
                + "WHERE r.post_id = ?";

        List<Reaction> reactions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        reactions.add(mapReaction(rs));
                    }
                }
            }

            return reactions;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve reactions.", e);
        }
    }

    private Reaction mapReaction(ResultSet rs) throws SQLException {

        Timestamp timestamp = rs.getTimestamp("created_at");

        return new Reaction(
                rs.getInt("reaction_id"),
                rs.getInt("post_id"),
                rs.getString("username"),
                ReactionType.parse(rs.getString("reaction_type")),
                timestamp.toLocalDateTime()
        );
    }
public void deleteByPost(int postId) {
        String sql = "DELETE FROM reactions WHERE post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete post reactions.", e);
        }
    }
}