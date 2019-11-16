package com.junction.vk.service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.junction.vk.cache.ProductCache;
import com.junction.vk.cache.UserProfileCache;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.domain.api.Product;
import com.junction.vk.domain.api.ProductDescription;
import com.junction.vk.domain.api.ProductWrapper;
import com.junction.vk.repository.http.VkProductRepository;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final UserProfileCache profileCache;
    private final ProductCache productCache;
    private final VkProductRepository productRepository;

    public ProductService(UserProfileCache profileCache, ProductCache productCache,
            VkProductRepository productRepository) {
        this.profileCache = profileCache;
        this.productCache = productCache;
        this.productRepository = productRepository;
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

        Collection<ProductWrapper> productsPayload = productRepository
                .getProductPayloadByToken(profile.getAccessToken(), count, offset);

        return productsPayload
                .stream()
                .map(productWrapper -> {
                    Product product = productWrapper.getProduct();
                    ProductDescription description = productCache
                            .getProductDescriptionById(profile.getAccessToken(), product.getProductId());

                    logger.debug("Product: {}, description: {}.", product, description);

                    return new ProductCard(product.getProductId(), product.getCategoryId(), 0, 0,
                            product.getTitle(), description == null ? null : description.getDescription(),
                            product.getRating(), product.getImage());
                })
                .collect(Collectors.toList());
    }
}
