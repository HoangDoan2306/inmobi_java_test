package com.inmobivn.javatest;

import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.exception.NotEnoughTurnsException;
import com.inmobivn.javatest.repository.UserRepository;
import com.inmobivn.javatest.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldConsumeOneTurnAfterGuess() {
        User user = createUser(
                "SCR-PLAYER1",
                "player1",
                0,
                3
        );

        var result = gameService.guess(user, 3);

        assertThat(result.getTurns())
                .as("Every guess must consume exactly one turn")
                .isEqualTo(2);

        assertThat(result.getServerNumber())
                .isBetween(1, 5);
    }

    @Test
    void shouldIncreaseScoreByExactlyOneWhenGuessIsCorrect() {
        User user = createUser(
                "SCR-PLAYER2",
                "player2",
                5,
                2
        );

        var result = gameService.guess(user, 3);

        /*
         * This test cannot guarantee a random 5% win.
         * Therefore it only verifies the invariant:
         *
         * - correct -> score +1
         * - incorrect -> score unchanged
         */
        if (result.isCorrect()) {
            assertThat(result.getServerNumber())
                    .isEqualTo(3);

            assertThat(result.getScore())
                    .isEqualTo(6);
        } else {
            assertThat(result.getServerNumber())
                    .isNotEqualTo(3);

            assertThat(result.getScore())
                    .isEqualTo(5);
        }

        assertThat(result.getTurns())
                .isEqualTo(1);
    }

    @Test
    void shouldRejectWhenNoTurnsRemain() {
        User user = createUser(
                "SCR-PLAYER3",
                "player3",
                0,
                0
        );

        assertThatThrownBy(() -> gameService.guess(user, 3))
                .isInstanceOf(NotEnoughTurnsException.class);

        User refreshed = userRepository
                .findById(user.getId())
                .orElseThrow();

        assertThat(refreshed.getTurns())
                .isZero();

        assertThat(refreshed.getScore())
                .isZero();
    }

    @Test
    void shouldAddFiveTurnsWhenBuyingTurns() {
        User user = createUser(
                "SCR-PLAYER4",
                "player4",
                0,
                2
        );

        var result = gameService.buyTurns(user);

        assertThat(result.getTurns())
                .isEqualTo(7);
    }

    @Test
    void shouldNotConsumeTurnWhenThereAreNoTurns() {
        User user = createUser(
                "SCR-PLAYER5",
                "player5",
                10,
                0
        );

        assertThatThrownBy(() -> gameService.guess(user, 1))
                .isInstanceOf(NotEnoughTurnsException.class);

        User refreshed = userRepository
                .findById(user.getId())
                .orElseThrow();

        assertThat(refreshed.getTurns()).isZero();
        assertThat(refreshed.getScore()).isEqualTo(10);
    }

    private User createUser(
            String scrId,
            String username,
            int score,
            int turns
    ) {
        User user = new User();
        user.setScrId(scrId);
        user.setUsername(username);
        user.setPassword("hashed");
        user.setScore(score);
        user.setTurns(turns);

        return userRepository.saveAndFlush(user);
    }
}