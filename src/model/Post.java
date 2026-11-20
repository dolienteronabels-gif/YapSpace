package yapspace.model;

import java.time.LocalDateTime;

public class Post extends BaseEntity {

    private final String author;
    private String content;
    private Category category;
    private Feeling feeling;
    private LocalDateTime editedAt;

    public Post(int id, String author, String content, Category category, Feeling feeling,
                LocalDateTime createdAt, LocalDateTime editedAt) {
        super(id, createdAt);
        this.author = author;
        this.content = content;
        this.category = category;
        this.feeling = feeling;
        this.editedAt = editedAt;
    }

    public Post(int id, String author, String content, Category category, Feeling feeling) {
        this(id, author, content, category, feeling, LocalDateTime.now(), null);
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public boolean isEdited() {
        return editedAt != null;
    }

    public boolean isOwnedBy(String username) {
        return author.equalsIgnoreCase(username);
    }

    public void update(String content, Category category, Feeling feeling) {
        this.content = content;
        this.category = category;
        this.feeling = feeling;
        this.editedAt = LocalDateTime.now();
    }

    @Override
    public String getSummary() {
        return "Post #" + getId() + " by " + author + " [" + category.getLabel() + ", " + feeling.getLabel() + "]";
    }
}
