package com.nogueira4j.movies.battle.domain.game;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Round(Movie firstMovie, Movie secondMovie) {

    public static Round with(final Movie firstMovie, final Movie secondMovie) {
        return new Round(firstMovie, secondMovie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        if (firstMovie.equals(round.firstMovie())
                || firstMovie.equals(round.secondMovie())) {
            return secondMovie.equals(round.firstMovie())
                    || secondMovie.equals(round.secondMovie());
        }
        return false;
    }
}
