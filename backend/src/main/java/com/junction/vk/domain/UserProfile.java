package com.junction.vk.domain;

public class UserProfile {
    private final long userId;
    private final String miniAppToken;
    private final String accessToken;

    public UserProfile(long userId, String miniAppToken, String accessToken) {
        this.userId = userId;
        this.miniAppToken = miniAppToken;
        this.accessToken = accessToken;
    }

    public long getUserId() {
        return userId;
    }

    public String getMiniAppToken() {
        return miniAppToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
