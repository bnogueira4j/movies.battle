package com.nogueira4j.movies.battle.infrastructure.rank.persistence;

import com.nogueira4j.movies.battle.infrastructure.rank.persistence.RankJpaEntity;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RankRepository extends JpaRepository<RankJpaEntity, String> {

    public List<RankJpaEntity> findAllByOrderByScoredDesc();
}
