package com.junction.vk.domain;

import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private final ApiStatus status;
    @Nullable
    private final String message;

    private ApiResponse(ApiStatus status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public ApiStatus getStatus() {
        return status;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public static ApiResponse of(ApiStatus status) {
        return new ApiResponse(status, null);
    }

    public static ApiResponse of(ApiStatus status, String message) {
        return new ApiResponse(status, message);
    }
}
