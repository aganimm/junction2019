package com.junction.vk.domain.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryWrapperList {
    private final List<CategoryWrapper> response;

    @JsonCreator
    public CategoryWrapperList(@JsonProperty("response") List<CategoryWrapper> response) {
        this.response = response;
    }

    public List<CategoryWrapper> getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "CategoryWrapperList{" +
                "response=" + response +
                '}';
    }
}
