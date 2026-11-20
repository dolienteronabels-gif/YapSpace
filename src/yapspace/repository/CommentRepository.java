package yapspace.repository;

import database.DatabaseConnection;
import yapspace.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    public int save(int postId, String author, String content) {
        String sql = "INSERT INTO comments (post_id, user_id, comment_text) "
                + "VALUES (?, (SELECT user_id FROM users WHERE username = ?), ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("Database connection failed.");

            try (PreparedStatement stmt = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, postId);
                stmt.setString(2, author);
                stmt.setString(3, content);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
            throw new SQLException("Could not retrieve comment ID.");

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save comment.", e);
        }
    }

    public Comment findById(int commentId) {
        String sql = "SELECT c.*, u.username FROM comments c "
                + "JOIN users u ON c.user_id = u.user_id "
                + "WHERE c.comment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("Database connection failed.");

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, commentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return mapComment(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find comment.", e);
        }
    }

    public List<Comment> findByPost(int postId) {
        String sql = "SELECT c.*, u.username FROM comments c "
                + "JOIN users u ON c.user_id = u.user_id "
                + "WHERE c.post_id = ? "
                + "ORDER BY c.created_at, c.comment_id";

        List<Comment> comments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("Database connection failed.");

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) comments.add(mapComment(rs));
                }
            }
            return comments;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve comments.", e);
        }
    }

    public void update(Comment comment) {
        String sql = "UPDATE comments SET comment_text = ?, "
                + "updated_at = CURRENT_TIMESTAMP WHERE comment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("Database connection failed.");

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, comment.getContent());
                stmt.setInt(2, comment.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update comment.", e);
        }
    }

    public void delete(int commentId) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("Database connection failed.");

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, commentId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete comment.", e);
        }
    }

    public void deleteByPost(int postId) {
        String sql = "DELETE FROM comments WHERE post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) throw new SQLException("Database connection failed.");

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete post comments.", e);
        }
    }

    private Comment mapComment(ResultSet rs) throws SQLException {
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");

        return new Comment(
                rs.getInt("comment_id"),
                rs.getInt("post_id"),
                rs.getString("username"),
                rs.getString("comment_text"),
                created.toLocalDateTime(),
                updated == null ? null : updated.toLocalDateTime()
        );
    }
}