package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedDto {
    private final long productId;
    private final boolean isLicked;

    @JsonCreator
    public FeedDto(@JsonProperty("productId") long productId, @JsonProperty("isLicked") boolean isLicked) {
        this.productId = productId;
        this.isLicked = isLicked;
    }

    public long getProductId() {
        return productId;
    }

    public boolean isLicked() {
        return isLicked;
    }

    @Override
    public String toString() {
        return "FeedDto{" +
                "productId=" + productId +
                ", isLicked=" + isLicked +
                '}';
    }
}
