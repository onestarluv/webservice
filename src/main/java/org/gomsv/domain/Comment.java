package org.gomsv.domain;

import java.time.LocalDateTime;

public class Comment {

    private final Long id;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment(Long id, String content) {
        this.id = id;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void update(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
