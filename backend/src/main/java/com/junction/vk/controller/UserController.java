package com.junction.vk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.junction.vk.domain.response.ApiResponse;
import com.junction.vk.domain.response.ApiResponseMiniAppToken;
import com.junction.vk.domain.response.ApiStatus;
import com.junction.vk.domain.dto.RegistrationDto;
import com.junction.vk.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    @CrossOrigin(origins = "*")
    public ResponseEntity<? extends ApiResponse> newUserRegistration(@RequestBody RegistrationDto registrationDto) {
        logger.info("Invoke newUserRegistration({}).", registrationDto);

        String securityToken = userService.getSecurityToken(registrationDto.getUserId(), registrationDto.getAccessToken());

        if (securityToken == null) {
            return ResponseEntity.ok(ApiResponse.of(ApiStatus.ACCESS_DENIED));
        }

        boolean operationExecuted = userService.newUserRegistration(registrationDto.getUserId(), securityToken,
                registrationDto.getAccessToken());

        ApiResponse response = operationExecuted ? ApiResponseMiniAppToken.of(ApiStatus.USER_CREATED, securityToken) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR);
        return ResponseEntity.ok(response);
    }
}
