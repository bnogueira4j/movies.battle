package com.nogueira4j.movies.battle.application.game.create;

import com.nogueira4j.movies.battle.domain.game.Round;

import java.util.List;

public record CreateGameOutput(String gameId, List<RoundOutput> round){
    public static CreateGameOutput from(
            final String gameId,
            final Round round
    ) {
        final var firstMovieOutput = RoundOutput.from(round.getFirstMovie().getId(), round.getFirstMovie().getTittle());
        final var secondMovieOutput = RoundOutput.from(round.getSecondMovie().getId(), round.getSecondMovie().getTittle());

        return new CreateGameOutput(gameId, List.of(firstMovieOutput, secondMovieOutput));
    }

    public record RoundOutput(String movieId, String title){

        public static RoundOutput from(
                final String movieId,
                final String title
        ) {
            return new RoundOutput(movieId, title);
        }
    }
}
