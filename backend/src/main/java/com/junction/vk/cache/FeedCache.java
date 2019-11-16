package com.junction.vk.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.junction.vk.domain.Feed;
import com.junction.vk.repository.db.EventRepository;

import static com.junction.vk.configuration.ThreadPoolConfiguration.DEFAULT_SCHEDULER;

@Service
public class FeedCache {
    private static final Logger logger = LoggerFactory.getLogger(FeedCache.class);

    private final Map<Long, Collection<Feed>> feedCache = new ConcurrentHashMap<>();
    private final EventRepository eventRepository;

    public FeedCache(@Qualifier(DEFAULT_SCHEDULER) ScheduledExecutorService executorService,
            EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        executorService.schedule(this::updateFeedCache, 30, TimeUnit.SECONDS);
    }

    public Collection<Feed> getFeedByUserId(long userId) {
        return feedCache.getOrDefault(userId, Collections.emptyList());
    }

    private void updateFeedCache() {
        feedCache.putAll(eventRepository.getUsersFeed());
        logger.debug("Fresh feed data: {}.", feedCache);
    }
}
