package yapspace.model;

import java.time.LocalDateTime;

public class Reaction extends BaseEntity {

    private final int postId;
    private final String username;
    private ReactionType type;

    public Reaction(int id, int postId, String username, ReactionType type, LocalDateTime createdAt) {
        super(id, createdAt);
        this.postId = postId;
        this.username = username;
        this.type = type;
    }

    public Reaction(int id, int postId, String username, ReactionType type) {
        this(id, postId, username, type, LocalDateTime.now());
    }

    public int getPostId() {
        return postId;
    }

    public String getUsername() {
        return username;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    @Override
    public String getSummary() {
        return username + " reacted " + type.getLabel() + " on post #" + postId;
    }
}
