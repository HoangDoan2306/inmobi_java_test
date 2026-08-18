package com.inmobivn.javatest;

import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.repository.UserRepository;
import com.inmobivn.javatest.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GuessConcurrencyIntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldOnlyConsumeOneTurnAcrossConcurrentRequests() throws InterruptedException, ExecutionException {
        User user = new User();
        user.setUsername("concurrent-player");
        user.setPassword("hashed");
        user.setScore(0);
        user.setTurns(1);
        user = userRepository.save(user);
        final User savedUser = user;

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futures.add(executor.submit(() -> {
                try {
                    gameService.guess(savedUser, 3);
                } catch (Exception ignored) {
                    // expected for failed concurrent attempts
                }
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        executor.shutdown();

        User refreshed = userRepository.findById(user.getId()).orElseThrow();
        assertThat(refreshed.getTurns()).isGreaterThanOrEqualTo(0);
        assertThat(refreshed.getScore()).isBetween(0, 1);
    }
}
