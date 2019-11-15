package com.junction.vk.domain.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductWrapper {
    private final Product product;

    @JsonCreator
    public ProductWrapper(@JsonProperty("product") Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "FeedPayload{" +
                "product=" + product +
                '}';
    }
}
