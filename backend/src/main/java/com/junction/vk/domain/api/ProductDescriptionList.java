package com.junction.vk.domain.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDescriptionList {
    private final List<ProductDescription> response;

    @JsonCreator
    public ProductDescriptionList(@JsonProperty("response") List<ProductDescription> response) {
        this.response = response;
    }

    public List<ProductDescription> getResponse() {
        return response;
    }
}
