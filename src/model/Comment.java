package yapspace.model;

import java.time.LocalDateTime;

public class Comment extends BaseEntity {

    private final int postId;
    private final String author;
    private String content;
    private LocalDateTime editedAt;

    public Comment(int id, int postId, String author, String content,
                   LocalDateTime createdAt, LocalDateTime editedAt) {
        super(id, createdAt);
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.editedAt = editedAt;
    }

    public Comment(int id, int postId, String author, String content) {
        this(id, postId, author, content, LocalDateTime.now(), null);
    }

    public int getPostId() {
        return postId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public boolean isEdited() {
        return editedAt != null;
    }

    public void edit(String newContent) {
        this.content = newContent;
        this.editedAt = LocalDateTime.now();
    }

    @Override
    public String getSummary() {
        return "Comment #" + getId() + " by " + author + " on post #" + postId;
    }
}
