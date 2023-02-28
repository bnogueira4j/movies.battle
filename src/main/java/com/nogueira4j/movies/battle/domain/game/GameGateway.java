package com.nogueira4j.movies.battle.domain.game;

import java.util.Optional;

public interface GameGateway {
    Game create(Game game);

    Game update(Game game);

    Optional<Game> findById(String id);

    Round createRound();
}
