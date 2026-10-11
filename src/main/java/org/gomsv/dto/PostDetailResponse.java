package org.gomsv.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.gomsv.domain.Post;

public record PostDetailResponse(
        Long id,
        String title,
        String content,
        List<CommentResponse> comments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostDetailResponse from(Post post) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getComments().stream()
                        .map(CommentResponse::from)
                        .toList(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
