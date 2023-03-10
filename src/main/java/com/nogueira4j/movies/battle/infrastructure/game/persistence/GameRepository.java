package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GameRepository extends JpaRepository<GameJpaEntity, String> {
}
