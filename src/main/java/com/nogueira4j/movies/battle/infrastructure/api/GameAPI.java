package com.nogueira4j.movies.battle.infrastructure.api;

import com.nogueira4j.movies.battle.application.game.create.CreateGameOutput;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameOutput;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameOutput;
import com.nogueira4j.movies.battle.application.rank.get.GetRankOutput;
import com.nogueira4j.movies.battle.infrastructure.game.models.CreateGameRequest;
import com.nogueira4j.movies.battle.infrastructure.game.models.UpdateGameRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "games")
@Tag(name = "Game")
@OpenAPIDefinition(info = @Info(title = "Movies Battle Game API"))
public interface GameAPI {

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create and start a new game")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created successfully",
                    content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                array = @ArraySchema(schema = @Schema(implementation = CreateGameOutput.class)
                            )
                    )

            ),
            @ApiResponse(responseCode = "404", description = "Player was nor found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> start();

    @PostMapping(value = "/{gameId}/next",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update game and start a new round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New round started with success",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UpdateGameOutput.class)
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "Game or Round was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> next(@PathVariable String gameId, @RequestBody UpdateGameRequest request);

    @PostMapping(value = "/{gameId}/finish",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Finish game and update score rank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Finished a game with success",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = FinishGameOutput.class)
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "Game or Round was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> finish(@PathVariable String gameId);

    @GetMapping(value = "/rank",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get all score rank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got a list of rank with success",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GetRankOutput.class)
                            )
                    )),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> rank();
}
