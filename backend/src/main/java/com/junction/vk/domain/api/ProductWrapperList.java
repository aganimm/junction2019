package com.junction.vk.domain.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductWrapperList {
    private final List<ProductWrapper> response;

    @JsonCreator
    public ProductWrapperList(@JsonProperty("response") List<ProductWrapper> response) {
        this.response = response;
    }

    public List<ProductWrapper> getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "ProductWrapperList{" +
                "response=" + response +
                '}';
    }
}
