package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomProductListDto {
    private final String title;

    @JsonCreator
    public CustomProductListDto(@JsonProperty("title") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "CustomProductListDto{" +
                "title='" + title + '\'' +
                '}';
    }
}
