package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import com.nogueira4j.movies.battle.domain.game.Movie;
import com.nogueira4j.movies.battle.domain.rank.Rank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Movie")
@Table(name = "movie")
public class MovieJpaEntity {

    @Id
    private String id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "rates", nullable = false)
    private Double rates;
    @Column(name = "votes", nullable = false)
    private Long votes;

    public MovieJpaEntity() {

    }

    public MovieJpaEntity(String id, String title, Double rates, Long votes) {
        this.id = id;
        this.title = title;
        this.rates = rates;
        this.votes = votes;
    }

    public static MovieJpaEntity from(final Movie movie) {
        return new MovieJpaEntity(
                movie.getId(),
                movie.getTittle(),
                movie.getRates(),
                movie.getVotes()
        );
    }

    public Movie toAggregate() {
        return Movie.with(
                getId(),
                getTitle(),
                getRates(),
                getVotes()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRates() {
        return rates;
    }

    public void setRates(Double rates) {
        this.rates = rates;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}





















