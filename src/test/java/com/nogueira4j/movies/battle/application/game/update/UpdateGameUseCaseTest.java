package com.nogueira4j.movies.battle.application.game.update;

import com.nogueira4j.movies.battle.application.UserCaseTest;
import com.nogueira4j.movies.battle.domain.exceptions.NotFoundException;
import com.nogueira4j.movies.battle.domain.exceptions.ScoreErrorsExceededException;
import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.movie.MovieGateway;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.domain.player.PlayerGateway;
import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class UpdateGameUseCaseTest extends UserCaseTest {

    @InjectMocks
    private DefaultUpdateGameUseCase useCase;

    @Mock
    private GameGateway gameGateway;

    @Mock
    private RankGateway rankGateway;

    @Mock
    private PlayerGateway playerGateway;

    @Mock
    private MovieGateway movieGateway;

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @Test
    public void givenAValidGame_whenCallsUpdateGame_shouldReturnNewRoundGameUpdated() {
        // given

        final var playerId = "player1";
        final var winningMovie = Movie.with("movie1", "Movie 1", 8.5, 100L);
        final var loserMovie = Movie.with("movie2", "Movie 2", 5.5, 100L);

        final var expectedGameId = "39fbec72-b760-11ed-afa1-0242ac120002";
        final var expectedMovieOne = Movie.with("movie3");
        final var expectedMovieTwo = Movie.with("movie4");
        final var expectedSizeRounds = 2;
        final var expectedScore = 1;
        final var expectedError = 0;
        final var expectRound = Round.with(expectedMovieOne, expectedMovieTwo);

        final var game = Game.with(
                UUID.fromString(expectedGameId),
                0,
                0,
                List.of(Round.with(
                        Movie.with("1"),
                        Movie.with("2"))
                ),
                playerId,
                false);

        when(gameGateway.findById(any()))
                .thenReturn(Optional.of(
                        game
                ));

        when(movieGateway.findById(any()))
                .thenReturn(Optional.of(winningMovie)).thenReturn(Optional.of(loserMovie));

        when(gameGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        when(gameGateway.createRound())
                .thenReturn(expectRound);

        final var aCommand =
                UpdateGameCommand.with(expectedGameId, winningMovie.getId() , loserMovie.getId());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedSizeRounds, actualOutput.round().size());
        Assertions.assertEquals(expectedMovieOne.getId(), actualOutput.round().get(0).movieId());
        Assertions.assertEquals(expectedMovieTwo.getId(), actualOutput.round().get(1).movieId());

        Mockito.verify(gameGateway, times(1)).findById(any());
        Mockito.verify(movieGateway, times(2)).findById(any());

        Mockito.verify(gameGateway, times(1)).update(argThat(aGame ->
                Objects.equals(expectedGameId, aGame.getId().toString())
                        &&  Objects.equals(expectedSizeRounds, aGame.getRounds().size())
                        &&  Objects.equals(expectedScore, aGame.getScore())
                        &&  Objects.equals(expectedError, aGame.getErrors())
        ));
    }

    @Test
    public void givenAInValidGame_whenCallsUpdateGame_shouldThrowNotFoundException() {
        // given
        final var winningMovie = Movie.with("movie1", "Movie 1", 8.5, 100L);
        final var loserMovie = Movie.with("movie2", "Movie 2", 5.5, 100L);

        final var expectedGameId = "39fbec72-b760-11ed-afa1-0242ac120002";

        final var aCommand =
                UpdateGameCommand.with(expectedGameId, winningMovie.getId() , loserMovie.getId());

        // when
        final var actualException =  Assertions.assertThrows(NotFoundException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);

        Mockito.verify(gameGateway, times(1)).findById(any());
        Mockito.verify(movieGateway, times(0)).findById(any());
        Mockito.verify(gameGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidGame_whenCallsUpdateGameAndMaximumError_shouldReturnScoreErrorsExceededException() {
        // given

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        final var expectPlayer = Optional.of(Player.with(1L, "Ciclano", "123"));
        when(authentication.getName()).thenReturn(expectPlayer.get().username());
        Mockito.when(playerGateway.findByUsername(any())).thenReturn(expectPlayer);

        final var playerId = "player1";
        final var winningMovie = Movie.with("movie1", "Movie 1", 5.5, 100L);
        final var loserMovie = Movie.with("movie2", "Movie 2", 8.5, 100L);

        final var expectedGameId = "39fbec72-b760-11ed-afa1-0242ac120002";

        final var game = Game.with(
                UUID.fromString(expectedGameId),
                0,
                3,
                List.of(Round.with(
                        Movie.with("1"),
                        Movie.with("2"))
                ),
                playerId,
                false);

        when(gameGateway.findById(any()))
                .thenReturn(Optional.of(
                        game
                ));

        when(movieGateway.findById(any()))
                .thenReturn(Optional.of(winningMovie)).thenReturn(Optional.of(loserMovie));

        when(gameGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());


        final var aCommand =
                UpdateGameCommand.with(expectedGameId, winningMovie.getId() , loserMovie.getId());

        // when
        final var actualException =  Assertions.assertThrows(ScoreErrorsExceededException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);

        Mockito.verify(gameGateway, times(1)).findById(any());
        Mockito.verify(movieGateway, times(2)).findById(any());
        Mockito.verify(gameGateway, times(1)).update(any());
    }
}
