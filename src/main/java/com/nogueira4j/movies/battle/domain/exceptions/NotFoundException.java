package com.nogueira4j.movies.battle.domain.exceptions;

public class NotFoundException extends DomainException {
    protected NotFoundException(final String aMessage) {
        super(aMessage);
    }

    public static NotFoundException with(
            final Class<?> aggregate,
            final String id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                aggregate.getSimpleName(),
                id
        );
        return new NotFoundException(anError);
    }


}
