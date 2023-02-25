package com.nogueira4j.movies.battle.infrastructure.api;

import com.nogueira4j.movies.battle.infrastructure.game.models.CreateGameRequest;
import com.nogueira4j.movies.battle.infrastructure.game.models.UpdateGameRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "games")
public interface GameAPI {

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> start();

    @PostMapping(value = "/{gameId}/next",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> next(@PathVariable String gameId, @RequestBody UpdateGameRequest request);

    @PostMapping(value = "/{gameId}/finish",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> finish(@PathVariable String gameId);

    @GetMapping(value = "/rank",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> rank();
}
