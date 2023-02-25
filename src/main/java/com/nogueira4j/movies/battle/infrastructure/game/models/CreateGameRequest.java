package com.nogueira4j.movies.battle.infrastructure.game.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateGameRequest(
        @JsonProperty("player_id") String playerId
) {
}
