package com.junction.vk.domain;

public class Feed {
    private final long userId;
    private final long productId;
    private final FeedStatus feed;

    public Feed(long userId, long productId, FeedStatus feed) {
        this.userId = userId;
        this.productId = productId;
        this.feed = feed;
    }

    public long getUserId() {
        return userId;
    }

    public long getProductId() {
        return productId;
    }

    public FeedStatus getFeed() {
        return feed;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", feed=" + feed +
                '}';
    }
}
