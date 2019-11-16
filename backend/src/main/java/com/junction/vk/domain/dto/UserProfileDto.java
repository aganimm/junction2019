package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junction.vk.domain.LookingForType;

public class UserProfileDto {
    private final String description;
    private final LookingForType lookingFor;

    @JsonCreator
    public UserProfileDto(@JsonProperty("description") String description,
            @JsonProperty("lookingFor") String lookingFor) {
        this.description = description;
        this.lookingFor = LookingForType.findTypeByName(lookingFor);
    }

    public String getDescription() {
        return description;
    }

    public LookingForType getLookingFor() {
        return lookingFor;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                ", description='" + description + '\'' +
                ", lookingFor=" + lookingFor +
                '}';
    }
}
