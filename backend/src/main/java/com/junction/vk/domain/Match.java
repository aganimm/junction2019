package com.junction.vk.domain;

public class Match {
    private final long userId;
    private final double percent;

    public Match(long userId, double percent) {
        this.userId = userId;
        this.percent = percent;
    }

    public long getUserId() {
        return userId;
    }

    public double getPercent() {
        return percent;
    }
}
