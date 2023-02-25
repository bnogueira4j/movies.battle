package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoundID implements Serializable {

    @Column(name = "first_movie_id", nullable = false)
    private String firstMovieId;

    @Column(name = "second_movie_id", nullable = false)
    private String secondMovieId;

    public RoundID() {
    }

    private RoundID(String firstMovieId, String secondMovieId) {
        this.firstMovieId = firstMovieId;
        this.secondMovieId = secondMovieId;
    }

    public static RoundID from(final String firstMovieId, final String secondMovieId) {
        return new RoundID(firstMovieId, secondMovieId);
    }

    public String getFirstMovieId() {
        return firstMovieId;
    }

    public void setFirstMovieId(String firstMovieId) {
        this.firstMovieId = firstMovieId;
    }

    public String getSecondMovieId() {
        return secondMovieId;
    }

    public void setSecondMovieId(String secondMovieId) {
        this.secondMovieId = secondMovieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundID roundID = (RoundID) o;
        return Objects.equals(getFirstMovieId(), roundID.getFirstMovieId()) && Objects.equals(getSecondMovieId(), roundID.getSecondMovieId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstMovieId(), getSecondMovieId());
    }
}
