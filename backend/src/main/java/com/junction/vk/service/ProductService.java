package com.junction.vk.service;

import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.junction.vk.cache.UserProfileCache;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.UserProfile;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final UserProfileCache profileCache;

    public ProductService(UserProfileCache profileCache) {
        this.profileCache = profileCache;
    }

    public Collection<ProductCard> getProductCards(long count, long offset, String miniAppToken) {
        if (!StringUtils.hasText(miniAppToken)) {
            logger.debug("Mini app token is empty.");
            return Collections.emptyList();
        }

        UserProfile profile = profileCache.getUserProfileByMiniAppToken(miniAppToken);

        if (profile == null) {
            logger.debug("Can't get profile by token: {}.", miniAppToken);
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

    private Collection<ProductCard> getProductCards() {
        return Collections.emptyList();
    }
}
