package com.junction.vk.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    protected final ApiStatus status;

    public ApiResponse(ApiStatus status) {
        this.status = status;
    }

    public ApiStatus getStatus() {
        return status;
    }

    public static ApiResponse of(ApiStatus status) {
        return new ApiResponse(status);
    }
}
