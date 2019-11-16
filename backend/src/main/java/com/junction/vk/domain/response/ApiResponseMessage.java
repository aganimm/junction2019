package com.junction.vk.domain.response;

public class ApiResponseMessage extends ApiResponse {
    private final String message;

    public ApiResponseMessage(ApiStatus status, String message) {
        super(status);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiResponseMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
