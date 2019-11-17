package com.junction.vk.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.junction.vk.domain.Feed;
import com.junction.vk.domain.FeedStatus;
import com.junction.vk.repository.db.FeedRepository;

import static com.junction.vk.configuration.ThreadPoolConfiguration.DEFAULT_SCHEDULER;

@Service
public class FeedCache {
    private static final Logger logger = LoggerFactory.getLogger(FeedCache.class);

    private final Map<Long, Collection<Feed>> feedCache = new ConcurrentHashMap<>();
    private final Map<Long, Double> feedInPercent = new ConcurrentHashMap<>();
    private final FeedRepository feedRepository;

    public FeedCache(@Qualifier(DEFAULT_SCHEDULER) ScheduledExecutorService executorService,
            FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
        feedCache.putAll(feedRepository.getUsersFeed());
        feedCache.forEach((k, v) -> {
            long size = v.size();
            long count = v.stream().filter(feed -> feed.getFeed() == FeedStatus.LIKE).count();

            double percent = (double) count / size;

            feedInPercent.put(k, percent);
        });
        executorService.schedule(this::updateFeedCache, 5, TimeUnit.SECONDS);
    }

    public Collection<Feed> getFeedByUserId(long userId) {
        return feedCache.getOrDefault(userId, Collections.emptyList());
    }

    private void updateFeedCache() {
        feedCache.putAll(feedRepository.getUsersFeed());
        logger.debug("Fresh feed data: {}.", feedCache);
    }

    public boolean updateFeed(long userId, long productId, FeedStatus feed) {
        return feedRepository.setFeed(userId, productId, feed);
    }

    public List<Pair<Long, Double>> getCommonFeed(long userId) {
        Double percent = feedInPercent.get(userId);

        logger.debug("User id: {}, feed percent: {}.", userId, percent);
        List<Pair<Long, Double>> idToPercent = new ArrayList<>();
        feedInPercent.forEach((k, v) -> {
            logger.debug("Id: {}, percent: {}.", k, v);
            if (Math.abs(v - percent) < 0.20) {
                idToPercent.add(Pair.of(k, 1 - Math.abs(v - percent)));
            }
        });

        return idToPercent;
    }
}
