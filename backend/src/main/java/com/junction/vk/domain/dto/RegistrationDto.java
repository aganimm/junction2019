package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationDto {
    private final long userId;
    private final String miniAppToken;
    private final String accessToken;

    @JsonCreator
    public RegistrationDto(@JsonProperty("miniAppToken") String miniAppToken,
            @JsonProperty("accessToken") String accessToken,
            @JsonProperty("userId") long userId) {
        this.userId = userId;
        this.miniAppToken = miniAppToken;
        this.accessToken = accessToken;
    }

    public String getMiniAppToken() {
        return miniAppToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "miniAppToken='" + miniAppToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", userId=" + userId +
                '}';
    }
}
