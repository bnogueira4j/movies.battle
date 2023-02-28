package com.nogueira4j.movies.battle.application.game.finish;

import com.nogueira4j.movies.battle.application.UserCaseTest;
import com.nogueira4j.movies.battle.domain.exceptions.NotFoundException;
import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class FinishGameUseCaseTest extends UserCaseTest {

    @InjectMocks
    private DefaultFinishGameUseCase useCase;

    @Mock
    private GameGateway gameGateway;

    @Mock
    private RankGateway rankGateway;

    @Test
    public void givenAValidGame_whenCallsFinishGame_shouldReturnGameFinalized() {
        // given
        final var expectedPlayerId = "player1";
        final var expectedGameFinished = true;
        final var expectedGame = Game.with(expectedPlayerId, Round.with(Movie.with("1"), Movie.with("2")));
        final var expectedGameId = expectedGame.getId().toString();

        when(gameGateway.findById(any()))
                .thenReturn(Optional.of(
                        expectedGame
                ));

        when(gameGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var aCommand =
                FinishGameCommand.with(expectedGameId);

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedGameId, actualOutput.gameId());
        Assertions.assertEquals(expectedPlayerId, actualOutput.playerId());
        Assertions.assertEquals(expectedGame.getScore(), actualOutput.scored());

        Mockito.verify(gameGateway, times(1)).findById(any());

        Mockito.verify(gameGateway, times(1)).update(argThat(aGame ->
                Objects.equals(expectedGameId, aGame.getId().toString())
                        &&  Objects.equals(expectedPlayerId, aGame.getPlayerId())
                        &&  Objects.equals(expectedGameFinished, aGame.isFinished())
        ));

        Mockito.verify(rankGateway, times(1)).save(argThat(aRank ->
                Objects.equals(expectedPlayerId, aRank.playerId())
                        &&  Objects.equals(expectedGame.getScore(), aRank.scored())
        ));
    }

    @Test
    public void givenAInValidGame_whenCallsFinishGame_shouldThrowNotFoundException() {
        // given
        final var expectedGameId = "gameId";

        final var aCommand =
                FinishGameCommand.with(expectedGameId);

        // when
        final var actualException =  Assertions.assertThrows(NotFoundException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);

        Mockito.verify(gameGateway, times(1)).findById(any());
        Mockito.verify(gameGateway, times(0)).update(any());
        Mockito.verify(rankGateway, times(0)).save(any());
    }
}
