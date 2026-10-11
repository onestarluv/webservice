package org.gomsv.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
        @NotBlank(message = "content는 빈 값일 수 없습니다.")
        String content
) {
}
