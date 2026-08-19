package com.inmobivn.javatest.scheduler;

import com.inmobivn.javatest.dto.LeaderboardEntryDto;
import com.inmobivn.javatest.service.GameService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaderboardScheduler {

    private final GameService gameService;

    public LeaderboardScheduler(GameService gameService) {
        this.gameService = gameService;
    }

    @Scheduled(fixedRate = 60000)
    @CachePut(value = "leaderboard", key = "'top10'")
    public List<LeaderboardEntryDto> refreshLeaderboardCache() {
        return gameService.getLeaderboard();
    }
}