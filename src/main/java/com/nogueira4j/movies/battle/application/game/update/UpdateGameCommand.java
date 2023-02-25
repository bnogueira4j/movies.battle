package com.nogueira4j.movies.battle.application.game.update;

public record UpdateGameCommand(String gameId, String winningMovie, String loserMovie){
    public static UpdateGameCommand with(
            final String gameId,
            final String winningMovie,
            final String loserMovie
    ) {
        return new UpdateGameCommand(gameId, winningMovie, loserMovie);
    }
}
