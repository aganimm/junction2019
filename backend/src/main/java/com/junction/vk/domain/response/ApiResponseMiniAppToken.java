package com.junction.vk.domain.response;

public class ApiResponseMiniAppToken extends ApiResponse {
    private final String miniAppToken;

    public ApiResponseMiniAppToken(ApiStatus status, String miniAppToken) {
        super(status);
        this.miniAppToken = miniAppToken;
    }

    public String getMiniAppToken() {
        return miniAppToken;
    }

    public static ApiResponseMiniAppToken of(ApiStatus status, String miniAppToken) {
        return new ApiResponseMiniAppToken(status, miniAppToken);
    }

    @Override
    public String toString() {
        return "ApiResponseMiniAppToken{" +
                "miniAppToken='" + miniAppToken + '\'' +
                '}';
    }
}
