package com.nogueira4j.movies.battle.infrastructure.player.persistence;

import com.nogueira4j.movies.battle.infrastructure.game.persistence.GameJpaEntity;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerJpaEntity, String> {
    Optional<PlayerJpaEntity> findPlayerByUsernameAndPassword(String username, String password);
    Optional<PlayerJpaEntity> findPlayerByUsername(String username);
}
