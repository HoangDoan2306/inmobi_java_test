package com.inmobivn.javatest.scheduler;

import com.inmobivn.javatest.dto.LeaderboardEntryDto;
import com.inmobivn.javatest.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LeaderboardScheduler {

    private final GameService gameService;
    private final CacheManager cacheManager;

    @Scheduled(fixedRate = 60000)
    public void refreshLeaderboardCache() {
        List<LeaderboardEntryDto> freshData = gameService.loadLeaderboardFromDb();
        if (!freshData.isEmpty()) {
            Cache leaderboardCache = cacheManager.getCache("leaderboard");
            if (leaderboardCache != null) {
                leaderboardCache.put("top10", freshData);
            }
        }
    }
}