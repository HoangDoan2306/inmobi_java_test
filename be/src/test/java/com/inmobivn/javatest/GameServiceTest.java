package com.inmobivn.javatest; 
 
import com.inmobivn.javatest.entity.User; 
import com.inmobivn.javatest.exception.NotEnoughTurnsException; 
import com.inmobivn.javatest.repository.UserRepository; 
import com.inmobivn.javatest.service.GameService; 
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
 
    @Test 
    void shouldIncreaseScoreWhenGuessIsCorrect() { 
        User user = new User(); 
        user.setScrId("SCR-PLAYER1");
        user.setUsername("player1"); 
        user.setPassword("hashed"); 
        user.setScore(0); 
        user.setTurns(3); 
        user = userRepository.save(user); 
 
        var result = gameService.guess(user, 1); 
 
        assertThat(result.getServerNumber()).isBetween(1, 5); 
        assertThat(result.getTurns()).isEqualTo(2); 
 
        if (result.isCorrect()) { 
            assertThat(result.getScore()).isEqualTo(1); 
        } else { 
            assertThat(result.getScore()).isEqualTo(0); 
        } 
    } 
 
    @Test 
    void shouldConsumeTurnAndKeepScoreWhenGuessIsIncorrect() { 
        User user = new User(); 
        user.setScrId("SCR-PLAYER2");
        user.setUsername("player2"); 
        user.setPassword("hashed"); 
        user.setScore(5); 
        user.setTurns(2); 
        user = userRepository.save(user); 
    
        var result = gameService.guess(user, 5); 
    
        assertThat(result.getServerNumber()).isBetween(1, 5); 
        assertThat(result.getTurns()).isEqualTo(1); 
    
        if (result.isCorrect()) { 
            assertThat(result.getScore()).isEqualTo(6); 
        } else { 
            assertThat(result.getScore()).isEqualTo(5); 
        } 
    }
 
    @Test 
    void shouldRejectWhenNoTurnsRemain() { 
        User user = new User(); 
        user.setScrId("SCR-PLAYER3");
        user.setUsername("player3"); 
        user.setPassword("hashed"); 
        user.setScore(0); 
        user.setTurns(0); 
        user = userRepository.save(user); 
 
        final User savedUser = user; 
 
        assertThatThrownBy(() -> gameService.guess(savedUser, 3)) 
            .isInstanceOf(NotEnoughTurnsException.class); 
    } 
 
    @Test 
    void shouldAddFiveTurnsWhenBuyingTurns() { 
        User user = new User(); 
        user.setScrId("SCR-PLAYER4");
        user.setUsername("player4"); 
        user.setPassword("hashed"); 
        user.setScore(0); 
        user.setTurns(2); 
        user = userRepository.save(user); 
 
        var result = gameService.buyTurns(user); 
 
        assertThat(result.getTurns()).isEqualTo(7); 
    } 
}