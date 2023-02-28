package com.nogueira4j.movies.battle.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nogueira4j.movies.battle.application.game.create.CreateGameOutput;
import com.nogueira4j.movies.battle.application.game.create.CreateGameUseCase;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameOutput;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameUseCase;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameOutput;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameUseCase;
import com.nogueira4j.movies.battle.application.rank.get.GetRankOutput;
import com.nogueira4j.movies.battle.application.rank.get.GetRankUseCase;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.rank.Rank;
import com.nogueira4j.movies.battle.infrastructure.api.controllers.PlayerController;
import com.nogueira4j.movies.battle.infrastructure.game.models.CreateGameRequest;
import com.nogueira4j.movies.battle.infrastructure.game.models.UpdateGameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class GameAPITest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CreateGameUseCase createGameUseCase;
    @MockBean
    private UpdateGameUseCase updateGameUseCase;
    @MockBean
    private FinishGameUseCase finishGameUseCase;
    @MockBean
    private GetRankUseCase getRankUseCase;

    @MockBean
    private PlayerController playerController;

    @Test
    public void givenAValidCommand_whenCallsCreateGame_shouldReturnGameIdAndRound() throws Exception {
        // given
        final var expectedGameId = "game-123";
        final var expectedMovieId = "movieId1";
        final var expectedMovieTitle = "The Movie of test";
        final var expectedMovieId2 = "movieId2";
        final var expectedMovieTitle2 = "The Movie of test 2";

        when(createGameUseCase.execute())
                .thenReturn(
                        CreateGameOutput.from(
                                expectedGameId,
                                Round.with(
                                        Movie.with(expectedMovieId, expectedMovieTitle, 2.5, 300L),
                                        Movie.with(expectedMovieId2, expectedMovieTitle2, 4.5, 500L)
                                )
                        ));

        // when
        final var request = post("/games")
                .header("Authorization", "Basic Q2ljbGFubzpjaWNsYW5vMTIz")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request);

        // then
        response.andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.gameId", equalTo(expectedGameId)))
                .andExpect(jsonPath("$.round.[0].movieId", equalTo(expectedMovieId)))
                .andExpect(jsonPath("$.round.[0].title", equalTo(expectedMovieTitle)))
                .andExpect(jsonPath("$.round.[1].movieId", equalTo(expectedMovieId2)))
                .andExpect(jsonPath("$.round.[1].title", equalTo(expectedMovieTitle2)));

        verify(createGameUseCase, times(1)).execute();
    }

    @Test
    public void givenAValidCommand_whenCallsNextGame_shouldReturnNewRound() throws Exception {
        // given
        final var expectedGameId = "game-123";
        final var expectedMovieId = "movieId1";
        final var expectedMovieTitle = "The Movie of test";
        final var expectedMovieId2 = "movieId2";
        final var expectedMovieTitle2 = "The Movie of test 2";

        final var input = new UpdateGameRequest(expectedGameId, expectedMovieId2);


        when(updateGameUseCase.execute(any()))
                .thenReturn(
                        UpdateGameOutput.from(
                                Round.with(
                                        Movie.with(expectedMovieId, expectedMovieTitle, 2.5, 300L),
                                        Movie.with(expectedMovieId2, expectedMovieTitle2, 4.5, 500L)
                                )
                        ));

        // when
        final var request = post("/games/%s/next".formatted(expectedGameId))
                .header("Authorization", "Basic Q2ljbGFubzpjaWNsYW5vMTIz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        final var response = this.mvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.round.[0].movieId", equalTo(expectedMovieId)))
                .andExpect(jsonPath("$.round.[0].title", equalTo(expectedMovieTitle)))
                .andExpect(jsonPath("$.round.[1].movieId", equalTo(expectedMovieId2)))
                .andExpect(jsonPath("$.round.[1].title", equalTo(expectedMovieTitle2)));

        verify(updateGameUseCase, times(1)).execute(any());
    }

    @Test
    public void givenAValidCommand_whenCallsFinishGame_shouldReturnGameFinished() throws Exception {
        // given
        final var expectedGameId = "game-123";
        final var expectedPlayerId = "player-1";
        final var expectedScored = 100;


        when(finishGameUseCase.execute(any()))
                .thenReturn(
                        FinishGameOutput.from(
                                expectedPlayerId, expectedGameId, expectedScored
                        ));

        // when
        final var request = post("/games/%s/finish".formatted(expectedGameId))
                .header("Authorization", "Basic Q2ljbGFubzpjaWNsYW5vMTIz")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.playerId", equalTo(expectedPlayerId)))
                .andExpect(jsonPath("$.gameId", equalTo(expectedGameId)))
                .andExpect(jsonPath("$.scored", equalTo(expectedScored)));

        verify(finishGameUseCase, times(1)).execute(any());
    }

    @Test
    public void givenAValidCommand_whenCallsRankGame_shouldReturnRankList() throws Exception {
        // given
        final var expectedRankOne = Rank.with("player1", 100);
        final var expectedRankTwo = Rank.with("player2", 89);
        final var expectedRankThree = Rank.with("player3", 70);

        when(getRankUseCase.execute())
                .thenReturn(
                        GetRankOutput.from(
                                List.of(
                                        expectedRankOne, expectedRankTwo, expectedRankThree
                                )
                        ));

        // when
        final var request = get("/games/rank")
                .header("Authorization", "Basic Q2ljbGFubzpjaWNsYW5vMTIz")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.rank.[0].playerId", equalTo(expectedRankOne.playerId())))
                .andExpect(jsonPath("$.rank.[0].scored", equalTo(expectedRankOne.scored())))
                .andExpect(jsonPath("$.rank.[1].playerId", equalTo(expectedRankTwo.playerId())))
                .andExpect(jsonPath("$.rank.[1].scored", equalTo(expectedRankTwo.scored())))
                .andExpect(jsonPath("$.rank.[2].playerId", equalTo(expectedRankThree.playerId())))
                .andExpect(jsonPath("$.rank.[2].scored", equalTo(expectedRankThree.scored())));

        verify(getRankUseCase, times(1)).execute();
    }
}
