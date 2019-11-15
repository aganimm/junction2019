package com.junction.vk.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.repository.db.UserRepository;

import static com.junction.vk.configuration.ThreadPoolConfiguration.USER_EXECUTOR;

@Service
public class UserProfileCache {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileCache.class);

    private static final long EXPIRE_AFTER_WHITE = Long.getLong("user.profile.cache.expire.after.write.min", 10);

    private final LoadingCache<String, UserProfile> tokenToProfileCache;

    public UserProfileCache(UserRepository userRepository, @Qualifier(USER_EXECUTOR) ExecutorService executorService) {

        tokenToProfileCache = Caffeine.newBuilder()
                .executor(executorService)
                .expireAfterWrite(EXPIRE_AFTER_WHITE, TimeUnit.MINUTES)
                .build(userRepository::findUserProfileByMiniAppToken);
    }

    @Nullable
    public UserProfile getUserProfileByMiniAppToken(String miniAppToken) {
        return tokenToProfileCache.get(miniAppToken);
    }
}
