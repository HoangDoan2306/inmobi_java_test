package com.inmobivn.javatest.controller;

import com.inmobivn.javatest.dto.GuessRequest;
import com.inmobivn.javatest.dto.GuessResponse;
import com.inmobivn.javatest.dto.LeaderboardEntryDto;
import com.inmobivn.javatest.dto.UserSummaryDto;
import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.security.CustomUserDetails;
import com.inmobivn.javatest.service.GameService;
import com.inmobivn.javatest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping("/game/guess")
    public ResponseEntity<GuessResponse> guess(@Valid @RequestBody GuessRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.getCurrentUser(userDetails.getScrId());
        return ResponseEntity.ok(gameService.guess(user, request.getGuess()));
    }

    @PostMapping("/game/buy-turns")
    public ResponseEntity<UserSummaryDto> buyTurns(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.getCurrentUser(userDetails.getScrId());
        return ResponseEntity.ok(gameService.buyTurns(user));
    }

    @GetMapping("/game/leaderboard")
    public ResponseEntity<List<LeaderboardEntryDto>> leaderboard() {
        return ResponseEntity.ok(gameService.getLeaderboard());
    }
}