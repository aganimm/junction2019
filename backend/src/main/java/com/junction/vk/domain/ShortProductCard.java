package com.junction.vk.domain;

public class ShortProductCard {
    private final long productId;
    private final String title;
    private final double price;
    private final String image;

    public ShortProductCard(long productId, String title, double price, String image) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public long getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ShortProductCard{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
