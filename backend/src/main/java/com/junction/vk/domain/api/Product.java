package com.junction.vk.domain.api;

import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junction.vk.util.ApiUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private final Price price;
    private final long ordersCount;
    private final double rating;
    private final String image;
    private final String title;
    private final String link;
    private final long productId;
    @Nullable
    private final Long categoryId;

    @JsonCreator
    public Product(@JsonProperty("price") Price price,
            @JsonProperty("orders_count") long ordersCount,
            @JsonProperty("rating") double rating,
            @JsonProperty("image") String image,
            @JsonProperty("title") String title,
            @JsonProperty("link") String link,
            @JsonProperty("category_id") @Nullable Long categoryId) {
        this.price = price;
        this.ordersCount = ordersCount;
        this.rating = rating;
        this.image = image;
        this.title = title;
        this.link = link;
        Long parsedId = ApiUtils.parseIdFromLink(link);
        this.productId = parsedId == null ? 0 : parsedId;
        this.categoryId = categoryId;
    }

    public Price getPrice() {
        return price;
    }

    public long getOrdersCount() {
        return ordersCount;
    }

    public double getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public long getProductId() {
        return productId;
    }

    @Nullable
    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", ordersCount=" + ordersCount +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", productId=" + productId +
                ", categoryId=" + categoryId +
                '}';
    }
}
