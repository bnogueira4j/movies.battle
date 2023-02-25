package com.nogueira4j.movies.battle.domain.player;

import java.util.Optional;

public interface PlayerGateway {
    Optional<Player> findByUsernameAndPassword(String username, String password);

    Optional<Player> findByUsername(String username);
}