package com.junction.vk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.junction.vk.repository.db.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean newUserRegistration(long userId, String miniAppToken, String accessToken) {
        if (!userRepository.existUserById(userId)) {
            logger.info("User with id: {} already exist.", userId);
            return userRepository.updateUser(userId, miniAppToken, accessToken);
        } else {
            return userRepository.createUser(userId, miniAppToken, accessToken);
        }
    }
}
