package com.nogueira4j.movies.battle.infrastructure.player;

import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.domain.player.PlayerGateway;
import com.nogueira4j.movies.battle.infrastructure.player.persistence.PlayerJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.player.persistence.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class PlayerGatewayH2 implements PlayerGateway {

    final PlayerRepository playerRepository;

    public PlayerGatewayH2(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> findByUsernameAndPassword(final String username, final String password) {
        return playerRepository.findPlayerByUsernameAndPassword(username, password).map(PlayerJpaEntity::toAggregate);
    }

    @Override
    public Optional<Player> findByUsername(String username) {
        return playerRepository.findPlayerByUsername(username).map(PlayerJpaEntity::toAggregate);
    }
}
