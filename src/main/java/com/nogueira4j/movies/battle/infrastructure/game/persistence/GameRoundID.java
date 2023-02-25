package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GameRoundID implements Serializable {
    @Column(name = "game_id", nullable = false)
    private String gameId;

    @Column(name = "round_id", nullable = false)
    private RoundID roundId;

    public GameRoundID() {
    }

    private GameRoundID(String gameId, RoundID roundId) {
        this.gameId = gameId;
        this.roundId = roundId;
    }

    public static GameRoundID from(final String gameId, final RoundID roundID) {
        return new GameRoundID(gameId, roundID);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public RoundID getRoundId() {
        return roundId;
    }

    public void setRoundId(RoundID roundId) {
        this.roundId = roundId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoundID that = (GameRoundID) o;
        return Objects.equals(getGameId(), that.getGameId()) && Objects.equals(getRoundId(), that.getRoundId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getRoundId());
    }
}
