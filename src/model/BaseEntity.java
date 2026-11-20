package yapspace.model;

import java.time.LocalDateTime;

public abstract class BaseEntity {

    private final int id;
    private final LocalDateTime createdAt;

    protected BaseEntity(int id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract String getSummary();

    @Override
    public String toString() {
        return getSummary();
    }
}
