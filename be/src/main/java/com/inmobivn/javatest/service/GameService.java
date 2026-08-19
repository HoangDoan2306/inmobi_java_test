package com.inmobivn.javatest.service;

import com.inmobivn.javatest.dto.GuessResponse;
import com.inmobivn.javatest.dto.LeaderboardEntryDto;
import com.inmobivn.javatest.dto.UserSummaryDto;
import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.exception.NotEnoughTurnsException;
import com.inmobivn.javatest.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final UserRepository userRepository;
    private final Random random = new Random();

    public GameService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles a guess with pessimistic locking to ensure concurrent requests
     * don't cause lost updates or negative turns.
     *
     * Strategy: Database-level PESSIMISTIC_WRITE lock acquired via findByScrIdForUpdate.
     * This ensures only one request can consume a turn, preventing race conditions
     * when multiple guesses arrive simultaneously for the same user.
     */
    @Transactional
    @CacheEvict(value = "user_profile", key = "#user.scrId")
    public GuessResponse guess(User user, Integer guess) {
        // Reload user with pessimistic lock by scrId to prevent concurrent turn consumption
        User lockedUser = userRepository.findByScrIdForUpdate(user.getScrId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with scrId: " + user.getScrId()));

        if (lockedUser.getTurns() <= 0) {
            throw new NotEnoughTurnsException("No turns remaining. Buy more turns to continue playing.");
        }

        // Consume turn atomically within transaction
        lockedUser.setTurns(lockedUser.getTurns() - 1);

        // Generate server number
        int serverNumber = random.nextInt(5) + 1; // 1-5

        // Check if correct
        boolean correct = guess.equals(serverNumber);
        if (correct) {
            lockedUser.setScore(lockedUser.getScore() + 1);
        }

        // Save changes
        userRepository.save(lockedUser);

        return new GuessResponse(correct, guess, serverNumber, lockedUser.getScore(), lockedUser.getTurns());
    }

    @Transactional
    @CacheEvict(value = "user_profile", key = "#user.scrId")
    public UserSummaryDto buyTurns(User user) {
        User lockedUser = userRepository.findByScrIdForUpdate(user.getScrId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with scrId: " + user.getScrId()));

        lockedUser.setTurns(lockedUser.getTurns() + 5);
        userRepository.save(lockedUser);

        return new UserSummaryDto(lockedUser.getScrId(), lockedUser.getScore(), lockedUser.getTurns());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "leaderboard", key = "'top10'")
    public List<LeaderboardEntryDto> getLeaderboard() {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findTop10ByOrderByScoreDescScrIdAsc(pageable).stream()
                .map(user -> new LeaderboardEntryDto(user.getUsername(), user.getScore()))
                .toList();
    }
}