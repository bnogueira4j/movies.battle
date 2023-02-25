package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.Movie;
import com.nogueira4j.movies.battle.domain.game.Round;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Game")
@Table(name = "game")
public class GameJpaEntity {

    @Id
    private String id;
    @Column(name = "score", nullable = false)
    private Integer score;
    @Column(name = "errors", nullable = false)
    private Integer errors;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<GameRoundJpaEntity> rounds;
    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "is_finished", nullable = false)
    private boolean isFinished;

    public GameJpaEntity() {

    }

    private GameJpaEntity(String id, Integer score, Integer errors, String playerId, boolean isFinished) {
        this.id = id;
        this.score = score;
        this.errors = errors;
        this.playerId = playerId;
        this.rounds = new ArrayList<>();
        this.isFinished = isFinished;
    }

    public static GameJpaEntity from(final Game game) {
        final var entity = new GameJpaEntity(
                game.getId().toString(),
                game.getScore(),
                game.getErrors(),
                game.getPlayerId(),
                game.isFinished()
        );
        game.getRounds().forEach(entity::addRound);

        return entity;
    }
    public Game toAggregate() {
        return Game.with(
                UUID.fromString(getId()),
                getScore(),
                getErrors(),
                getAggregateRounds(),
                getPlayerId(),
                isFinished()
        );
    }

    private void addRound(final Round round) {
        final var roundId = RoundID.from(round.firstMovie().getId(), round.secondMovie().getId());
        this.rounds.add(GameRoundJpaEntity.from(this, roundId));
    }

    public List<Round> getAggregateRounds() {
        return getRounds().stream()
                .map(it ->
                        Round.with(
                                Movie.with(
                                        it.getId().getRoundId().getFirstMovieId()
                                ),
                                Movie.with(
                                        it.getId().getRoundId().getSecondMovieId()
                        )
                ))
                .toList();
    }
    public String getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getErrors() {
        return errors;
    }

    public List<GameRoundJpaEntity> getRounds() {
        return rounds;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public void setRounds(List<GameRoundJpaEntity> rounds) {
        this.rounds = rounds;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}





















