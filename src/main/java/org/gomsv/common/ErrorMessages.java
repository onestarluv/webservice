package org.gomsv.common;

public final class ErrorMessages {

    public static final String POST_NOT_FOUND = "게시글을 찾을 수 없습니다.";
    public static final String COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";
    public static final String INVALID_REQUEST = "요청 값이 올바르지 않습니다.";
    public static final String INVALID_JSON = "JSON 형식이 올바르지 않습니다.";
    public static final String INVALID_ID = "id는 양수 정수여야 합니다.";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 오류가 발생했습니다.";
    public static final String SERVICE_UNAVAILABLE = "서비스를 일시적으로 사용할 수 없습니다.";
    public static final String SIMULATED_INTERNAL_ERROR = "시뮬레이션된 500 오류";

    private ErrorMessages() {
    }
}
