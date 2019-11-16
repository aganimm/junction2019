package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junction.vk.domain.LookingForType;

public class UserProfileDto {
    private final String description;
    private final LookingForType lookingFor;
    private final String sex;

    @JsonCreator
    public UserProfileDto(@JsonProperty("description") String description,
            @JsonProperty("lookingFor") String lookingFor,
            @JsonProperty("sex") String sex) {
        this.description = description;
        this.lookingFor = LookingForType.findTypeByName(lookingFor);
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public LookingForType getLookingFor() {
        return lookingFor;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "description='" + description + '\'' +
                ", lookingFor=" + lookingFor +
                ", sex='" + sex + '\'' +
                '}';
    }
}
