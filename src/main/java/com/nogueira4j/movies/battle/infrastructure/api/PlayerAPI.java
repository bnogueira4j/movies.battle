package com.nogueira4j.movies.battle.infrastructure.api;

import com.nogueira4j.movies.battle.infrastructure.player.models.LoginPlayerRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "player")
public interface PlayerAPI {

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> login(@RequestBody LoginPlayerRequest request);
}
