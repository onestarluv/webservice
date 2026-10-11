package org.gomsv.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Post {

    private final Long id;
    private String title;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<Comment> comments = new ArrayList<>();

    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void replace(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        this.updatedAt = LocalDateTime.now();
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
