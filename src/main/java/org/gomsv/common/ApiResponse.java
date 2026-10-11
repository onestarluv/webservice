package org.gomsv.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {

    private static final Object OMITTED_DATA = new Object();

    private final String status;
    private final Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    private ApiResponse(String status, Object data, Integer statusCode, String message) {
        this.status = status;
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data, null, null);
    }

    public static ApiResponse<Void> error(int statusCode, String message) {
        return new ApiResponse<>("error", OMITTED_DATA, statusCode, message);
    }

    public String getStatus() {
        return status;
    }

    @JsonProperty("data")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = OmittedDataFilter.class)
    public Object getData() {
        return data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public static class OmittedDataFilter {

        @Override
        public boolean equals(Object other) {
            return other == OMITTED_DATA;
        }
    }
}
