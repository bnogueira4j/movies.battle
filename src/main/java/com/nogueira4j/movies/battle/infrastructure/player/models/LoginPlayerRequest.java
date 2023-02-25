package com.nogueira4j.movies.battle.infrastructure.player.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginPlayerRequest(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
) {
}
