package com.junction.vk.service;

import java.util.UUID;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.junction.vk.domain.LookingForType;
import com.junction.vk.domain.ProductListItem;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.domain.response.ApiStatus;
import com.junction.vk.repository.db.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ProductService productService;

    public UserService(UserRepository userRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

    public ApiStatus newUserRegistration(long userId, String miniAppToken, String accessToken) {
        if (userRepository.existUserById(userId)) {
            logger.info("User with id: {} already exist.", userId);
            if (userRepository.updateUser(userId, miniAppToken, accessToken)) {
                return ApiStatus.USER_UPDATED;
            }
        } else {
            boolean isCreated = userRepository.createUser(userId, miniAppToken, accessToken);

            if (isCreated) {
                if (productService.createProductList("Default", ProductListItem.ProductListType.DEFAULT,
                        miniAppToken) == null) {
                    logger.warn("Can't create default product list for user: {}.", userId);
                }
            }

            return ApiStatus.USER_CREATED;
        }
        return ApiStatus.INTERNAL_ERROR;
    }

    @Nullable
    public String getSecurityToken(long userId, String accessToken) {
        //TODO Validation token
        return UUID.randomUUID().toString();
    }

    public UserProfile getUserProfileByToken(String miniAppToken) {
        return userRepository.findUserProfileByMiniAppToken(miniAppToken);
    }

    public ApiStatus updateUserProfile(LookingForType lookingFor, String description, String miniAppToken) {
        UserProfile profile = getUserProfileByToken(miniAppToken);

        if (profile == null) {
            return ApiStatus.INTERNAL_ERROR;
        }
        return userRepository.updatePersonalInfo(profile.getUserId(), lookingFor, description) ? ApiStatus.USER_UPDATED
                : ApiStatus.INTERNAL_ERROR;
    }
}
