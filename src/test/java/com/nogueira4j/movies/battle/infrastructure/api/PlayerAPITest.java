package com.nogueira4j.movies.battle.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nogueira4j.movies.battle.application.game.create.CreateGameOutput;
import com.nogueira4j.movies.battle.application.game.create.CreateGameUseCase;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameOutput;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameUseCase;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameOutput;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameUseCase;
import com.nogueira4j.movies.battle.application.player.login.LoginPlayerCommand;
import com.nogueira4j.movies.battle.application.player.login.LoginPlayerOutput;
import com.nogueira4j.movies.battle.application.player.login.LoginPlayerUseCase;
import com.nogueira4j.movies.battle.application.rank.get.GetRankOutput;
import com.nogueira4j.movies.battle.application.rank.get.GetRankUseCase;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.rank.Rank;
import com.nogueira4j.movies.battle.infrastructure.api.controllers.GameController;
import com.nogueira4j.movies.battle.infrastructure.api.controllers.PlayerController;
import com.nogueira4j.movies.battle.infrastructure.game.models.UpdateGameRequest;
import com.nogueira4j.movies.battle.infrastructure.player.models.LoginPlayerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PlayerAPITest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private LoginPlayerUseCase loginPlayerUseCase;
    @MockBean
    private GameController gameController;

    @Test
    public void givenAValidCommand_whenCallsLoginPlayer_shouldReturnTokenAuthentication() throws Exception {
        // given
        final var expectedUsername = "user";
        final var expectedPassword = "password";
        final var expectedAuthorization = "token-123";

        final var input = new LoginPlayerRequest(expectedUsername, expectedPassword);

        when(loginPlayerUseCase.execute(LoginPlayerCommand.with(expectedUsername, expectedPassword)))
                .thenReturn(
                        LoginPlayerOutput.from(
                                expectedAuthorization
                        ));

        // when
        final var request = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        final var response = this.mvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.authorization", equalTo(expectedAuthorization)));

        verify(loginPlayerUseCase, times(1)).execute(any());
    }
}
