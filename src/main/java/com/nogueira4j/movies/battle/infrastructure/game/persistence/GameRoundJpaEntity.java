package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "games_rounds")
@Table(name = "games_rounds")
public class GameRoundJpaEntity {
    @EmbeddedId
    private GameRoundID id;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @MapsId("gameId")
    private GameJpaEntity game;

    public GameRoundJpaEntity() {
    }

    public GameRoundJpaEntity(final GameJpaEntity game, final RoundID roundId, final String status) {
        this.id = GameRoundID.from(game.getId(), roundId);
        this.game = game;
        this.status = status;
    }

    public static GameRoundJpaEntity from(final GameJpaEntity game, final RoundID roundId, final String status) {
        return new GameRoundJpaEntity(game, roundId, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoundJpaEntity that = (GameRoundJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public GameRoundID getId() {
        return id;
    }

    public void setId(GameRoundID id) {
        this.id = id;
    }

    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
