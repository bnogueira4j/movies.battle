package com.nogueira4j.movies.battle.domain.game;

import com.nogueira4j.movies.battle.domain.exceptions.RoundAlreadyExistsException;
import com.nogueira4j.movies.battle.domain.exceptions.ScoreErrorsExceededException;
import org.jmolecules.ddd.annotation.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AggregateRoot
public class Game {
    private final UUID id;
    private Integer score;
    private Integer errors;
    private List<Round> rounds;
    private final String playerId;
    private boolean isFinished;

    private Game(final String playerId, List<Round> rounds) {
        this.id = UUID.randomUUID();
        this.score = 0;
        this.errors = 0;
        this.rounds = rounds;
        this.playerId = playerId;
        this.isFinished = false;
    }

    private Game(final UUID id,
                 final Integer score,
                 final Integer errors,
                 final List<Round> rounds,
                 final String playerId,
                 final Boolean isFinished) {
        this.id = id;
        this.score = score;
        this.errors = errors;
        this.rounds = rounds;
        this.playerId = playerId;
        this.isFinished = isFinished;
    }

    public static Game with(
            final String playerId,
            final Round round
    ) {
        return new Game(playerId, List.of(round));
    }

    public static Game with(
            final UUID id,
            final Integer score,
            final Integer errors,
            final List<Round> rounds,
            final String playerId,
            final Boolean isFinished
    ) {
        return new Game(
                id,
                score,
                errors,
                rounds,
                playerId,
                isFinished
        );
    }

    public UUID getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getErrors() {
        return errors;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void finished() {
        this.isFinished = true;
    }

    public Game addRound(Round round) {
        final var newRounds = new ArrayList<>(getRounds());
        newRounds.add(round);
        this.rounds = newRounds;
        return this;
    }

    public Game updateScore(boolean isCorrect) {
        if(isCorrect) {
            this.score += 1;
        } else {
            this.errors += 1;
            validateErrors();
        }
        return this;
    }
    private void validateErrors() {
        if(this.errors > 1) {
            finished();
           throw ScoreErrorsExceededException.with(playerId, id.toString());
        }
    }

    public void validateRound(Round newRound) {
        if(checkRoundExists(newRound)) {
            throw RoundAlreadyExistsException.with(
                    getId().toString(),
                    newRound.getFirstMovie().getId(),
                    newRound.getSecondMovie().getId()
            );
        }
    }
    private Boolean checkRoundExists(Round newRound) {
        return this.rounds.stream().anyMatch(newRound::equals);
    }
}
