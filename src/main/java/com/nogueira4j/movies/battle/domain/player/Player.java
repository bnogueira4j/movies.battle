package com.nogueira4j.movies.battle.domain.player;

import org.jmolecules.ddd.annotation.AggregateRoot;

@AggregateRoot
public record Player(Long id, String username, String password) {

    public static Player with(
            final Long id,
            final String username,
            final String password
    ) {
        return new Player(
                id,
                username,
                password
        );
    }
}
