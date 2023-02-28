package com.nogueira4j.movies.battle.domain.movie;

import org.jmolecules.ddd.annotation.Entity;

@Entity
public class Movie {
    private String id;
    private String tittle;
    private Double rates;
    private Long votes;

    private Movie(String id, String tittle, Double rates, Long votes) {
        this.id = id;
        this.tittle = tittle;
        this.rates = rates;
        this.votes = votes;
    }

    private Movie(String id) {
        this.id = id;
    }

    public static Movie with(
            final String id,
            final String tittle,
            final Double rates,
            final Long votes
    ) {
        return new Movie(
                id,
                tittle,
                rates,
                votes
        );
    }

    public static Movie with(
            final String id
    ) {
        return new Movie(
                id
        );
    }

    public String getId() {
        return id;
    }

    public String getTittle() {
        return tittle;
    }

    public Double getRates() {
        return rates;
    }

    public Long getVotes() {
        return votes;
    }

    public boolean isWinner(final Movie competitorMovie) {
        final var points = getRates() * getVotes();
        final var competitorPoints = competitorMovie.rates * competitorMovie.getVotes();
        return points >= competitorPoints;
    }
}
