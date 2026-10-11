package org.gomsv.dto;

import java.time.LocalDateTime;
import org.gomsv.domain.Post;

public record PostSummaryResponse(
        Long id,
        String title,
        String content,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostSummaryResponse from(Post post) {
        return new PostSummaryResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getComments().size(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
