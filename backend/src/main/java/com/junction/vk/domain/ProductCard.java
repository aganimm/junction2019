package com.junction.vk.domain;

public class ProductCard {
    private final long productId;
    private final long price;
    private final String title;
    private final double rating;
    private final String image;

    public ProductCard(long productId, long price, String title,
            double rating, String image) {
        this.productId = productId;
        this.price = price;
        this.title = title;
        this.rating = rating;
        this.image = image;
    }

    public long getProductId() {
        return productId;
    }

    public long getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
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
                ", price=" + price +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                '}';
    }
}
