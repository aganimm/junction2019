package com.junction.vk.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.junction.vk.cache.FeedCache;
import com.junction.vk.domain.LookingForType;
import com.junction.vk.domain.Match;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.domain.response.ApiStatus;
import com.junction.vk.repository.db.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final FeedCache feedCache;

    public UserService(UserRepository userRepository, FeedCache feedCache) {
        this.userRepository = userRepository;
        this.feedCache = feedCache;
    }

    public ApiStatus newUserRegistration(long userId, String miniAppToken, String accessToken) {
        if (userRepository.existUserById(userId)) {
            logger.info("User with id: {} already exist.", userId);
            if (userRepository.updateUser(userId, miniAppToken, accessToken)) {
                return ApiStatus.USER_UPDATED;
            }
        } else {
            boolean isCreated = userRepository.createUser(userId, miniAppToken, accessToken);
            return ApiStatus.USER_CREATED;
        }
        return ApiStatus.INTERNAL_ERROR;
    }

    @Nullable
    public String getSecurityToken(long userId, String accessToken) {
        //TODO Validation token
        return UUID.randomUUID().toString();
    }

    @Nullable
    public UserProfile getUserProfileByToken(String miniAppToken) {
        if (miniAppToken != null) {
            return userRepository.findUserProfileByMiniAppToken(miniAppToken);
        }
        return null;
    }

    public ApiStatus updateUserProfile(LookingForType lookingFor, String description, String sex, String miniAppToken) {
        UserProfile profile = getUserProfileByToken(miniAppToken);

        if (profile == null) {
            return ApiStatus.INTERNAL_ERROR;
        }
        return userRepository.updatePersonalInfo(profile.getUserId(), lookingFor, description, sex) ? ApiStatus.USER_UPDATED
                : ApiStatus.INTERNAL_ERROR;
    }

    public Collection<Match> getMatches(String miniAppToken) {
        UserProfile profile = getUserProfileByToken(miniAppToken);

        if (profile == null) {
            logger.error("Can't get matches for token: {}.", miniAppToken);
            return Collections.emptyList();
        }

        return feedCache
                .getCommonFeed(profile.getUserId())
                .stream()
                .map(pair -> new Match(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
    }
}
