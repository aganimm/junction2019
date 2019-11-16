package com.junction.vk.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.repository.db.ProductRepository;

import static com.junction.vk.configuration.ThreadPoolConfiguration.USER_EXECUTOR;

@Service
public class ProductCache {
    private static final long EXPIRE_AFTER_WHITE = Long.getLong("user.profile.cache.expire.after.write.min", 10);

    private final LoadingCache<Long, ProductCard> tokenToProductDescription;

    public ProductCache(ProductRepository productRepository,
            @Qualifier(USER_EXECUTOR) ExecutorService executorService) {

        tokenToProductDescription = Caffeine.newBuilder()
                .executor(executorService)
                .expireAfterWrite(EXPIRE_AFTER_WHITE, TimeUnit.MINUTES)
                .build(productRepository::findProductById);
    }

    @Nullable
    public ProductCard getProductDescriptionById(long productId) {
        return tokenToProductDescription.get(productId);
    }
}
