package com.inmobivn.javatest;

import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.exception.NotEnoughTurnsException;
import com.inmobivn.javatest.repository.UserRepository;
import com.inmobivn.javatest.service.GameService;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldAllowOnlyOneSuccessfulGuessWhenThereIsOneTurn()
            throws InterruptedException, ExecutionException {

        User user = new User();
        user.setScrId("SCR-CONCURRENT");
        user.setUsername("concurrent-player");
        user.setPassword("hashed");
        user.setScore(0);
        user.setTurns(1);

        user = userRepository.saveAndFlush(user);

        final String scrId = user.getScrId();

        ExecutorService executor =
                Executors.newFixedThreadPool(10);

        try {
            List<Future<Boolean>> futures = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                futures.add(
                        executor.submit(() -> {
                            try {
                                gameService.guess(
                                        new UserReference(scrId),
                                        3
                                );

                                return true;
                            } catch (NotEnoughTurnsException e) {
                                return false;
                            }
                        })
                );
            }

            int successCount = 0;

            for (Future<Boolean> future : futures) {
                if (future.get()) {
                    successCount++;
                }
            }

            /*
             * Only one request can consume the only available turn.
             */
            assertThat(successCount)
                    .isEqualTo(1);

            User refreshed = userRepository
                    .findByScrId(scrId)
                    .orElseThrow();

            assertThat(refreshed.getTurns())
                    .isZero();

            assertThat(refreshed.getScore())
                    .isBetween(0, 1);

        } finally {
            executor.shutdown();
        }
    }

    private static class UserReference extends User {

        UserReference(String scrId) {
            setScrId(scrId);
        }
    }
}