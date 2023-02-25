//package com.nogueira4j.movies.battle.infrastructure.game.persistence;
//
//import com.nogueira4j.movies.battle.domain.game.Game;
//import com.nogueira4j.movies.battle.domain.game.Round;
//
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.util.Objects;
//import java.util.UUID;
//
//@Entity(name = "Round")
//@Table(name = "round")
//public class RoundJpaEntity {
//    @EmbeddedId
//    private RoundID id;
//
//    public RoundJpaEntity() {
//
//    }
//    private RoundJpaEntity(RoundID id) {
//        this.id = id;
//    }
//
//    public static RoundJpaEntity from(final Round round) {
//        return new RoundJpaEntity(
//                RoundID.from(round.firstMovie(),
//                round.secondMovie())
//        );
//    }
//
//    public Round toAggregate() {
//        return Round.with(
//                getId().getFirstMovieId(),
//                getId().getSecondMovieId()
//        );
//    }
//
//    public RoundID getId() {
//        return id;
//    }
//
//    public void setId(RoundID id) {
//        this.id = id;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RoundJpaEntity that = (RoundJpaEntity) o;
//        return Objects.equals(getId(), that.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }
//}