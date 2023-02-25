package com.nogueira4j.movies.battle.domain.game;

import java.util.UUID;

public interface GameGateway {
    Game create(Game game);

    Game update(Game game);

    Game findById(String id);

    Round createRound();
}
