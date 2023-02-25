package com.nogueira4j.movies.battle.infrastructure.game.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateGameRequest(
        @JsonProperty("winner_movie") String winnerMovie,
        @JsonProperty("loser_movie") String loserMovie
) {
}
