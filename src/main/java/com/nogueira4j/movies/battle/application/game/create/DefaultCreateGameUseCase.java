package com.nogueira4j.movies.battle.application.game.create;

import com.nogueira4j.movies.battle.domain.exceptions.NotFoundException;
import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.domain.player.PlayerGateway;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DefaultCreateGameUseCase extends CreateGameUseCase {
    private final GameGateway gameGateway;
    private final PlayerGateway playerGateway;

    public DefaultCreateGameUseCase(final GameGateway gameGateway, PlayerGateway playerGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
        this.playerGateway = Objects.requireNonNull(playerGateway);
    }

    @Override
    public CreateGameOutput execute() {
        final var username = SecurityContextHolder.getContext().getAuthentication().getName();
        final var player = playerGateway.findByUsername(username).orElseThrow(() -> NotFoundException.with(Player.class, username));
        final var round = gameGateway.createRound();
        final var game = Game.with(player.id().toString());
        game.addRound(round);

        gameGateway.create(game);
        return CreateGameOutput.from(game.getId().toString(), round);
    }
}
