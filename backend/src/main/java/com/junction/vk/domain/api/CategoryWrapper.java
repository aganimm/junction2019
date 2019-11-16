package com.junction.vk.domain.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryWrapper {
    private final long categoryId;
    private final String name;
    private final long parentId;

    @JsonCreator
    public CategoryWrapper(@JsonProperty("id") long categoryId,
            @JsonProperty("name") String name,
            @JsonProperty("parent_id") long parentId) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public long getParentId() {
        return parentId;
    }

    @Override
    public String toString() {
        return "CategoryWrapper{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
