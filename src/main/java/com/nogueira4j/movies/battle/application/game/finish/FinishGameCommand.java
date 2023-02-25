package com.nogueira4j.movies.battle.application.game.finish;

public record FinishGameCommand(String gameId){
    public static FinishGameCommand with(
            final String gameId
    ) {
        return new FinishGameCommand(gameId);
    }
}
