package com.nogueira4j.movies.battle.domain.rank;

import com.nogueira4j.movies.battle.domain.game.Round;
import org.jmolecules.ddd.annotation.AggregateRoot;

@AggregateRoot
public record Rank(String playerId, Integer scored) {

    public static Rank with(final String playerId, final Integer scored) {
        return new Rank(playerId, scored);
    }
}
