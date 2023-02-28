package com.nogueira4j.movies.battle.application.game.create;

import com.nogueira4j.movies.battle.application.UserCaseTest;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class CreateGameUseCaseTest extends UserCaseTest {

    @InjectMocks
    private DefaultCreateGameUseCase useCase;

    @Mock
    private GameGateway gameGateway;

    @Mock
    private PlayerGateway playerGateway;

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @Test
    public void givenAValidPlayer_whenCallsCreateGame_shouldReturnGameIdAndRound() {
        // given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        final var expectPlayer = Optional.of(Player.with(1L, "Ciclano", "123"));
        when(authentication.getName()).thenReturn(expectPlayer.get().username());
        Mockito.when(playerGateway.findByUsername(any())).thenReturn(expectPlayer);

        final var expectRound = Round.with(Movie.with("1"), Movie.with("2"));
        when(gameGateway.createRound())
                .thenReturn(expectRound);

        // when
        final var actualOutput = useCase.execute();

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.gameId());
        Assertions.assertNotNull(actualOutput.round());
        Assertions.assertEquals(2, actualOutput.round().size());

        Mockito.verify(playerGateway, times(1)).findByUsername(any());

        Mockito.verify(gameGateway, times(1)).create(argThat(aGame ->
                Objects.equals(expectRound, aGame.getRounds().get(0))
                        && Objects.equals(expectPlayer.get().id().toString(), aGame.getPlayerId())
                        && Objects.equals(expectRound.getFirstMovie().getId(), aGame.getRounds().get(0).getFirstMovie().getId())
                        && Objects.equals(expectRound.getSecondMovie().getId(), aGame.getRounds().get(0).getSecondMovie().getId())
                        && Objects.equals(expectRound.getStatusRound(), aGame.getRounds().get(0).getStatusRound())
        ));
    }

    @Test
    public void givenAInValidPlayer_whenCallsCreateGame_shouldThrowNotFoundException() {
        // given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn("invalidUser");
        Mockito.when(playerGateway.findByUsername(any())).thenThrow(NotFoundException.class);

        // when
        final var actualException =  Assertions.assertThrows(NotFoundException.class, () -> {
            useCase.execute();
        });

        // then
        Assertions.assertNotNull(actualException);

        Mockito.verify(playerGateway, times(1)).findByUsername(any());
        Mockito.verify(gameGateway, times(0)).create(any());
    }
}
