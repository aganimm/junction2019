package com.junction.vk.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junction.vk.domain.FeedStatus;

public class FeedDto {
    private final long productId;
    private final FeedStatus feed;

    @JsonCreator
    public FeedDto(@JsonProperty("productId") long productId, @JsonProperty("feed") String feed) {
        this.productId = productId;
        this.feed = FeedStatus.findFeedStatusByName(feed);
    }

    public long getProductId() {
        return productId;
    }

    public FeedStatus getFeed() {
        return feed;
    }

    @Override
    public String toString() {
        return "FeedDto{" +
                "productId=" + productId +
                ", isLicked=" + feed +
                '}';
    }
}
