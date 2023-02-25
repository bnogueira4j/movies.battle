package com.nogueira4j.movies.battle.domain.exceptions;

public class RoundAlreadyExistsException extends DomainException {
    protected RoundAlreadyExistsException(final String aMessage) {
        super(aMessage);
    }

    public static RoundAlreadyExistsException with(
            final String gameId,
            final String firstMovie,
            final String secondMovie
    ) {
        final var anError = "O game %s ja possui uma rodada com seguintes filmes: %s, %s".formatted(
                gameId,
                firstMovie,
                secondMovie
        );
        return new RoundAlreadyExistsException(anError);
    }
}
