package com.nogueira4j.movies.battle.domain.exceptions;

public class  ScoreErrorsExceededException extends DomainException {
    protected ScoreErrorsExceededException(final String aMessage) {
        super(aMessage);
    }

    public static ScoreErrorsExceededException with(
            final String playerId,
            final String gameId
    ) {
        final var anError = "O player %s atingiu maximo de erros no game %s".formatted(
                playerId,
                gameId
        );
        return new ScoreErrorsExceededException(anError);
    }
}
