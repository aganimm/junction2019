package com.junction.vk.controller;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.junction.vk.domain.Match;
import com.junction.vk.service.UserService;
import com.junction.vk.util.RequestUtils;

@RestController
@RequestMapping("/api/social")
public class SocialController {
    private final UserService userService;

    public SocialController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/match")
    public ResponseEntity<Collection<Match>> getMatches(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMatches(RequestUtils.getMiniAppToken(request)));
    }
}

