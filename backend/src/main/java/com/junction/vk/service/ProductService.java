package com.junction.vk.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.junction.vk.cache.FeedCache;
import com.junction.vk.cache.ProductCache;
import com.junction.vk.cache.UserProfileCache;
import com.junction.vk.domain.Feed;
import com.junction.vk.domain.FeedStatus;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.repository.db.ProductListRepository;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final UserProfileCache profileCache;
    private final ProductCache productCache;
    private final FeedCache feedCache;

    private final ProductListRepository productListRepository;

    public ProductService(UserProfileCache profileCache, ProductCache productCache,
            ProductListRepository productListRepository, FeedCache feedCache) {
        this.profileCache = profileCache;
        this.productCache = productCache;
        this.productListRepository = productListRepository;
        this.feedCache = feedCache;
    }

    public boolean updateFeed(long productId, FeedStatus feed, String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);

        if (profile == null) {
            return false;
        }
        return feedCache.updateFeed(profile.getUserId(), productId, feed);
    }

    public Collection<ProductCard> getProductCards(String miniAppToken) {
        return productCache.getProductCards();
    }

    //TODO Need use spring security
    private UserProfile getProfile(String miniAppToken) {
        if (StringUtils.hasText(miniAppToken)) {

            UserProfile profile = profileCache.getUserProfileByMiniAppToken(miniAppToken);

            if (profile == null) {
                logger.debug("Can't get profile by token: {}.", miniAppToken);
            }
            return profile;
        }
        logger.debug("Mini app token is empty.");
        return null;
    }

    public Collection<ProductCard> getProductCardsInCart(String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);

        if (profile == null) {
            logger.warn("Invoke getProductCardsInCart with empty token.");
            return Collections.emptyList();
        }

        return feedCache
                .getFeedByUserId(profile.getUserId())
                .stream()
                .filter(feed -> feed.getFeed() == FeedStatus.CART || feed.getFeed() == FeedStatus.LIKE)
                .map(feed -> productCache.getProductCardById(feed.getProductId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
