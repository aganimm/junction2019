package com.junction.vk.domain;

import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCard {
    private final long productId;
    @Nullable
    private final Long categoryId;
    private final double price;
    private final long currencyId;
    private final String title;
    @Nullable
    private final String description;
    private final double rating;
    private final String image;

    public ProductCard(long productId, Long categoryId, double price, long currencyId, String title,
            String description, double rating, String image) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.price = price;
        this.currencyId = currencyId;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public long getProductId() {
        return productId;
    }

    @Nullable
    public Long getCategoryId() {
        return categoryId;
    }

    public double getPrice() {
        return price;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ProductCard{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", currencyId=" + currencyId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                '}';
    }
}
