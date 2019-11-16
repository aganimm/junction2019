package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductIdDto {
    private final long productId;

    @JsonCreator
    public ProductIdDto(@JsonProperty("productId") long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "ProductIdDto{" +
                "productId=" + productId +
                '}';
    }
}
