package org.gomsv.dto;

import java.time.LocalDateTime;
import org.gomsv.domain.Comment;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
