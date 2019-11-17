package com.junction.vk.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.junction.vk.domain.LookingForType;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.domain.dto.RegistrationDto;
import com.junction.vk.domain.dto.UserProfileDto;
import com.junction.vk.domain.response.ApiResponse;
import com.junction.vk.domain.response.ApiResponseMiniAppToken;
import com.junction.vk.domain.response.ApiStatus;
import com.junction.vk.service.UserService;
import com.junction.vk.util.RequestUtils;

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

        ApiStatus apiStatus = userService.newUserRegistration(registrationDto.getUserId(), securityToken,
                registrationDto.getAccessToken());

        ApiResponse response = apiStatus != ApiStatus.INTERNAL_ERROR ?
                ApiResponseMiniAppToken.of(apiStatus, securityToken) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserProfileByToken(RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse> updateUserProfile(@RequestBody UserProfileDto userProfileDto,
            HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.of(userService.updateUserProfile(userProfileDto.getLookingFor(),
                userProfileDto.getDescription(), userProfileDto.getSex() == null ? LookingForType.NOT_KNOWN.getName()
                        : userProfileDto.getSex(),
                RequestUtils.getMiniAppToken(request))));
    }
}
