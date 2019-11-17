package com.junction.vk.domain;

import java.util.stream.Stream;

public enum FeedStatus {
    LIKE("LIKE"),
    DISLIKE("DISLIKE"),
    CART("CART");

    private final String name;

    FeedStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FeedStatus findFeedStatusByName(String name) {
        return Stream.of(values())
                .filter(feed -> feed.getName().equals(name))
                .findFirst()
                .orElse(DISLIKE);
    }
}
