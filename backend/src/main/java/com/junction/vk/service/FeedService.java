package com.junction.vk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.junction.vk.cache.FeedCache;

@Service
public class FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);

    private final FeedCache feedCache;

    public FeedService(FeedCache feedCache) {
        this.feedCache = feedCache;
    }
}
