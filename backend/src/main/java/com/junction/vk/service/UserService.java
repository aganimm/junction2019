package com.junction.vk.service;

import java.util.UUID;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.junction.vk.domain.ProductListItem;
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

    public boolean newUserRegistration(long userId, String miniAppToken, String accessToken) {
        if (userRepository.existUserById(userId)) {
            logger.info("User with id: {} already exist.", userId);
            return userRepository.updateUser(userId, miniAppToken, accessToken);
        } else {
            boolean isCreated = userRepository.createUser(userId, miniAppToken, accessToken);

            if (isCreated) {
                if (productService.createProductList("Default", ProductListItem.ProductListType.DEFAULT,
                        miniAppToken) == null) {
                    logger.warn("Can't create default product list for user: {}.", userId);
                }
            }

            return isCreated;
        }
    }

    @Nullable
    public String getSecurityToken(long userId, String accessToken) {
        //TODO Validation token
        return UUID.randomUUID().toString();
    }
}
