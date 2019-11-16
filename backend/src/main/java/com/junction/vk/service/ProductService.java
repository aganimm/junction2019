package com.junction.vk.service;

import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.junction.vk.cache.ProductCache;
import com.junction.vk.cache.UserProfileCache;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.ProductListItem;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.repository.db.ProductListRepository;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final UserProfileCache profileCache;
    private final ProductCache productCache;

    private final ProductListRepository productListRepository;

    public ProductService(UserProfileCache profileCache, ProductCache productCache, ProductListRepository productListRepository) {
        this.profileCache = profileCache;
        this.productCache = productCache;
        this.productListRepository = productListRepository;
    }

    public boolean updateFeed(long productId, boolean isLicked, String miniAppToken) {
        return false;
    }

    public Collection<ProductCard> getProductCards(String miniAppToken) {
        return productCache.getProductCards();
    }

    public Long createProductList(String title, ProductListItem.ProductListType productListType, String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);

        if (profile == null) {
            return null;
        }
        return productListRepository.createProductList(title, productListType, profile.getUserId());
    }

    public Collection<ProductListItem> getProductListItems(String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);
        if (profile == null) {
            return Collections.emptyList();
        }
        return productListRepository.getProductListItemsByUserId(profile.getUserId());
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
}
