package com.junction.vk.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.junction.vk.domain.api.ProductDescription;
import com.junction.vk.repository.db.DbProductRepository;
import com.junction.vk.repository.http.VkProductRepository;

import static com.junction.vk.configuration.ThreadPoolConfiguration.USER_EXECUTOR;

@Service
public class ProductCache {
    private static final Logger logger = LoggerFactory.getLogger(ProductCache.class);

    private static final long EXPIRE_AFTER_WHITE = Long.getLong("user.profile.cache.expire.after.write.min", 10);

    private final LoadingCache<Pair<String, Long>, ProductDescription> tokenToProductDescription;
    private final Map<Long, ProductDescription> productIdToDescription = new ConcurrentHashMap<>();

    public ProductCache(DbProductRepository dbProductRepository, VkProductRepository vkProductRepository,
            @Qualifier(USER_EXECUTOR) ExecutorService executorService) {

        tokenToProductDescription = Caffeine.newBuilder()
                .removalListener((RemovalListener<Pair<String, Long>, ProductDescription>) (key, value, cause) -> {
                    if (key != null && key.getKey() != null) {
                        logger.debug("Remove product description with id: {}.", key.getKey());
                        productIdToDescription.remove(key.getValue());
                    }
                })
                .executor(executorService)
                .expireAfterWrite(EXPIRE_AFTER_WHITE, TimeUnit.MINUTES)
                .build(pair -> {
                    ProductDescription description = productIdToDescription.get(pair.getValue());

                    if (description != null) {
                        return description;
                    }

                    ProductDescription productFromDb = dbProductRepository.findProductById(pair.getValue());

                    if (productFromDb != null) {
                        return productFromDb;
                    }

                    ProductDescription productFromApi = vkProductRepository
                            .getProductDescriptionByIds(pair.getKey(), pair.getValue());

                    if (productFromApi != null) {
                        productIdToDescription.put(pair.getValue(), productFromApi);
                    }

                    return productFromApi;
                });
    }

    @Nullable
    public ProductDescription getProductDescriptionById(String accessToken, long productId) {
        return tokenToProductDescription.get(Pair.of(accessToken, productId));
    }
}
