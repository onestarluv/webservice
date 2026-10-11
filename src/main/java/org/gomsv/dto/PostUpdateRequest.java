package org.gomsv.dto;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(
        @NotBlank(message = "title은 빈 값일 수 없습니다.")
        String title,

        @NotBlank(message = "content는 빈 값일 수 없습니다.")
        String content
) {
}
