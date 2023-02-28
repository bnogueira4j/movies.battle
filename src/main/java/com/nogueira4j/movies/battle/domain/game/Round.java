package com.nogueira4j.movies.battle.domain.game;

import com.nogueira4j.movies.battle.domain.movie.Movie;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public class Round {

    private final Movie firstMovie;
    private final Movie secondMovie;
    private StatusRound statusRound;

    private Round(Movie firstMovie, Movie secondMovie, StatusRound statusRound) {
        this.firstMovie = firstMovie;
        this.secondMovie = secondMovie;
        this.statusRound = statusRound;
    }

    public static Round with(final Movie firstMovie, final Movie secondMovie) {
        return new Round(firstMovie, secondMovie, StatusRound.IN_PROGRESS);
    }

    public static Round with(final Movie firstMovie, final Movie secondMovie, final StatusRound statusRound) {
        return new Round(firstMovie, secondMovie, statusRound);
    }

    public void finish(){
        this.statusRound = StatusRound.FINISHED;
    }

    public Movie getFirstMovie() {
        return firstMovie;
    }

    public Movie getSecondMovie() {
        return secondMovie;
    }

    public StatusRound getStatusRound() {
        return statusRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        if (firstMovie.getId().equals(round.getFirstMovie().getId())
                || firstMovie.getId().equals(round.getSecondMovie().getId())) {
            return secondMovie.getId().equals(round.getFirstMovie().getId())
                    || secondMovie.getId().equals(round.getSecondMovie().getId());
        }
        return false;
    }

    public enum StatusRound {
        FINISHED, IN_PROGRESS
    }
}
