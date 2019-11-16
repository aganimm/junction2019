package com.junction.vk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserProfile {
    private final long userId;
    private final String miniAppToken;
    private final String accessToken;
    private final LookingForType lookingFor;
    private final String description;

    public UserProfile(long userId, String miniAppToken, String accessToken,
            LookingForType lookingFor, String description) {
        this.userId = userId;
        this.miniAppToken = miniAppToken;
        this.accessToken = accessToken;
        this.lookingFor = lookingFor;
        this.description = description == null ? "" : description;
    }

    public long getUserId() {
        return userId;
    }

    @JsonIgnore
    public String getMiniAppToken() {
        return miniAppToken;
    }

    @JsonIgnore
    public String getAccessToken() {
        return accessToken;
    }

    public LookingForType getLookingFor() {
        return lookingFor;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId=" + userId +
                ", miniAppToken='" + miniAppToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", lookingFor=" + lookingFor +
                ", description='" + description + '\'' +
                '}';
    }
}
