package com.junction.vk.domain.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDescription {
    private final String productId;
    private final String description;

    @JsonCreator
    public ProductDescription(@JsonProperty("product_id") String productId,
            @JsonProperty("description") String description) {
        this.productId = productId;
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
                "productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
