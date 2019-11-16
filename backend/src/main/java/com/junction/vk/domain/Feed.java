package com.junction.vk.domain;

public class Feed {
    private final long userId;
    private final long productId;
    private final boolean isLiked;

    public Feed(long userId, long productId, boolean isLiked) {
        this.userId = userId;
        this.productId = productId;
        this.isLiked = isLiked;
    }

    public long getUserId() {
        return userId;
    }

    public long getProductId() {
        return productId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", isLiked=" + isLiked +
                '}';
    }
}
