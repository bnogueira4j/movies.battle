package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.rank.Rank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Rank")
@Table(name = "rank")
public class RankJpaEntity {

    @Id
    @Column(name = "playerId", nullable = false)
    private String playerId;
    @Column(name = "scored", nullable = false)
    private Integer scored;


    public RankJpaEntity() {
    }

    private RankJpaEntity(String playerId, Integer scored) {
        this.playerId = playerId;
        this.scored = scored;
    }

    public static RankJpaEntity from(final Rank rank) {
        return new RankJpaEntity(
                rank.playerId(),
                rank.scored()
        );
    }

    public Rank toAggregate() {
        return Rank.with(
                getPlayerId(),
                getScored()
        );
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getScored() {
        return scored;
    }

    public void setScored(Integer scored) {
        this.scored = scored;
    }
}





















