package com.nogueira4j.movies.battle.application.player.login;

import com.nogueira4j.movies.battle.domain.exceptions.NotFoundException;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.domain.player.PlayerGateway;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;

@Component
public class DefaultLoginPlayerUseCase extends LoginPlayerUseCase {
    private final PlayerGateway playerGateway;

    public DefaultLoginPlayerUseCase(final PlayerGateway playerGateway) {
        this.playerGateway = Objects.requireNonNull(playerGateway);
    }

    @Override
    public LoginPlayerOutput execute(LoginPlayerCommand command) {
        final var player = playerGateway.findByUsernameAndPassword(command.username(), command.password());
        final var token = player.map(p ->
                        Base64.getEncoder().encodeToString(
                                (p.username() + ":" + p.password())
                                        .getBytes()))
                .orElseThrow(() -> NotFoundException.with(Player.class, command.username()));
        return LoginPlayerOutput.from(token);
    }
}
