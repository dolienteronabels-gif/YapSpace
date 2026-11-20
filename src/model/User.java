package yapspace.model;

import java.time.LocalDateTime;

public abstract class User extends BaseEntity {

    private final String username;
    private String email;
    private final String salt;
    private final String passwordHash;

    protected User(int id, String username, String email, String salt, String passwordHash,
                   LocalDateTime createdAt) {
        super(id, createdAt);
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public abstract Role getRole();

    public abstract boolean canModerate();

    public abstract boolean canDelete(Post post);

    public abstract boolean canDelete(Comment comment);

    public boolean canEdit(Post post) {
        return post.isOwnedBy(username);
    }

    public boolean canEdit(Comment comment) {
        return comment.getAuthor().equalsIgnoreCase(username);
    }

    @Override
    public String getSummary() {
        return username + " (" + getRole().getLabel() + ")";
    }
}
