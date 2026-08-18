package com.inmobivn.javatest.controller;

import com.inmobivn.javatest.dto.UserSummaryDto;
import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserSummaryDto> getCurrentUser(Authentication authentication) {
        User user = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(new UserSummaryDto(user.getUsername(), user.getScore(), user.getTurns()));
    }
}
