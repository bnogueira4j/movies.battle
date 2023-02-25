package com.nogueira4j.movies.battle.application.game.finish;

public record FinishGameOutput(String playerId, String gameId, Integer scored){
    public static FinishGameOutput from(
            final String playerId,
            final String gameId,
            final Integer scored
    ) {
        return new FinishGameOutput(playerId, gameId, scored);
    }
}
