package com.junction.vk.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.repository.db.ProductRepository;

@Service
public class ProductCache {
    private static final Logger logger = LoggerFactory.getLogger(ProductCache.class);

    private final Map<Long, ProductCard> productIdToCard = new ConcurrentHashMap<>();

    public ProductCache(ProductRepository productRepository) {
        productRepository
                .findProducts()
                .forEach(productCard -> productIdToCard.put(productCard.getProductId(), productCard));

        logger.debug("Product cache state: {}.", productIdToCard);
    }

    @Nullable
    public ProductCard getProductCartById(long productId) {
        return productIdToCard.get(productId);
    }
}
