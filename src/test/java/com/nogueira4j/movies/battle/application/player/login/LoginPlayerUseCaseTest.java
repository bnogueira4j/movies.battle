package com.nogueira4j.movies.battle.application.player.login;

import com.nogueira4j.movies.battle.application.UserCaseTest;
import com.nogueira4j.movies.battle.application.game.create.DefaultCreateGameUseCase;
import com.nogueira4j.movies.battle.domain.exceptions.NotFoundException;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.domain.player.PlayerGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class LoginPlayerUseCaseTest extends UserCaseTest {

    @InjectMocks
    private DefaultLoginPlayerUseCase useCase;

    @Mock
    private PlayerGateway playerGateway;


    @Test
    public void givenAValidUserNameAndPassword_whenCallsLoginPlayer_shouldReturnTokenAuthentication() {
        // given
        final var expectedPlayerId = 1L;
        final var expectedUsername = "teste";
        final var expectedPassword = "teste123";
        final var expectedToken = Base64.getEncoder().encodeToString(
                (expectedUsername + ":" + expectedPassword)
                        .getBytes());

        final var player = Player.with(expectedPlayerId, expectedUsername, expectedPassword);
        when(playerGateway.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(player));

        final var command = LoginPlayerCommand.with(expectedUsername, expectedPassword);

        // when
        final var actualOutput = useCase.execute(command);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedToken, actualOutput.authorization());

        Mockito.verify(playerGateway, times(1)).findByUsernameAndPassword(any(), any());
    }

    @Test
    public void givenAInValidPlayer_whenCallsCreateGame_shouldThrowNotFoundException() {
        // given
        final var expectedUsername = "teste";
        final var expectedPassword = "teste123";

        when(playerGateway.findByUsernameAndPassword(any(), any())).thenReturn(Optional.empty());

        final var command = LoginPlayerCommand.with(expectedUsername, expectedPassword);

        // when
        final var actualException =  Assertions.assertThrows(NotFoundException.class, () -> {
            useCase.execute(command);
        });

        // then
        Assertions.assertNotNull(actualException);

        Mockito.verify(playerGateway, times(1)).findByUsernameAndPassword(any(), any());
    }
}
