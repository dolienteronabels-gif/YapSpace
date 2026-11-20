package yapspace.repository;

import database.DatabaseConnection;
import yapspace.model.Category;
import yapspace.model.Feeling;
import yapspace.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    public int save(String author, String content,
                    Category category, Feeling feeling) {

        String sql = "INSERT INTO posts "
                + "(user_id, category_id, feeling_id, content) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            int userId = getId(conn,
                    "SELECT user_id FROM users WHERE username = ?", author);

            int categoryId = getId(conn,
                    "SELECT category_id FROM categories WHERE category_name = ?",
                    category.name());

            int feelingId = getId(conn,
                    "SELECT feeling_id FROM feelings WHERE feeling_name = ?",
                    feeling.name());

            try (PreparedStatement stmt = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, userId);
                stmt.setInt(2, categoryId);
                stmt.setInt(3, feelingId);
                stmt.setString(4, content);

                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }

            throw new SQLException("Could not retrieve post ID.");

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save post.", e);
        }
    }

    public Post findById(int postId) {

        String sql = "SELECT p.*, u.username, "
                + "c.category_name, f.feeling_name "
                + "FROM posts p "
                + "JOIN users u ON p.user_id = u.user_id "
                + "LEFT JOIN categories c ON p.category_id = c.category_id "
                + "LEFT JOIN feelings f ON p.feeling_id = f.feeling_id "
                + "WHERE p.post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapPost(rs);
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find post.", e);
        }
    }

    public List<Post> findAll() {

        String sql = "SELECT p.*, u.username, "
                + "c.category_name, f.feeling_name "
                + "FROM posts p "
                + "JOIN users u ON p.user_id = u.user_id "
                + "LEFT JOIN categories c ON p.category_id = c.category_id "
                + "LEFT JOIN feelings f ON p.feeling_id = f.feeling_id "
                + "ORDER BY p.created_at DESC, p.post_id DESC";

        List<Post> posts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    posts.add(mapPost(rs));
                }
            }

            return posts;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve posts.", e);
        }
    }

    public List<Post> findByAuthor(String author) {

        List<Post> posts = new ArrayList<>();

        String sql = "SELECT p.*, u.username, "
                + "c.category_name, f.feeling_name "
                + "FROM posts p "
                + "JOIN users u ON p.user_id = u.user_id "
                + "LEFT JOIN categories c ON p.category_id = c.category_id "
                + "LEFT JOIN feelings f ON p.feeling_id = f.feeling_id "
                + "WHERE u.username = ? "
                + "ORDER BY p.created_at DESC, p.post_id DESC";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, author);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        posts.add(mapPost(rs));
                    }
                }
            }

            return posts;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve user posts.", e);
        }
    }

    public void update(Post post) {

        String sql = "UPDATE posts SET content = ?, "
                + "category_id = ?, feeling_id = ?, "
                + "updated_at = CURRENT_TIMESTAMP "
                + "WHERE post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            int categoryId = getId(conn,
                    "SELECT category_id FROM categories WHERE category_name = ?",
                    post.getCategory().name());

            int feelingId = getId(conn,
                    "SELECT feeling_id FROM feelings WHERE feeling_name = ?",
                    post.getFeeling().name());

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, post.getContent());
                stmt.setInt(2, categoryId);
                stmt.setInt(3, feelingId);
                stmt.setInt(4, post.getId());

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to update post.", e);
        }
    }

    public void delete(int postId) {

        String sql = "DELETE FROM posts WHERE post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete post.", e);
        }
    }

    private int getId(Connection conn, String sql, String value)
            throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException("Record not found: " + value);
    }

    private Post mapPost(ResultSet rs) throws SQLException {

        int id = rs.getInt("post_id");
        String author = rs.getString("username");
        String content = rs.getString("content");

        Category category =
                Category.parseOrNull(rs.getString("category_name"));

        Feeling feeling =
                Feeling.parseOrNull(rs.getString("feeling_name"));

        Timestamp createdTimestamp = rs.getTimestamp("created_at");
        Timestamp updatedTimestamp = rs.getTimestamp("updated_at");

        LocalDateTime createdAt =
                createdTimestamp.toLocalDateTime();

        LocalDateTime editedAt =
                updatedTimestamp == null
                        ? null
                        : updatedTimestamp.toLocalDateTime();

        return new Post(
                id, author, content,
                category, feeling, createdAt, editedAt
        );
    }
}