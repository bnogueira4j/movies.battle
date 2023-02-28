package com.nogueira4j.movies.battle.infrastructure.api.controllers;

import com.nogueira4j.movies.battle.application.player.login.LoginPlayerCommand;
import com.nogueira4j.movies.battle.application.player.login.LoginPlayerUseCase;
import com.nogueira4j.movies.battle.infrastructure.api.PlayerAPI;
import com.nogueira4j.movies.battle.infrastructure.player.models.LoginPlayerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
public class PlayerController implements PlayerAPI {

    private final LoginPlayerUseCase loginPlayerUseCase;

    public PlayerController(final LoginPlayerUseCase loginPlayerUseCase) {
        this.loginPlayerUseCase = Objects.requireNonNull(loginPlayerUseCase);
    }

    @Override
    public ResponseEntity<?> login(LoginPlayerRequest request) {
        final var command = LoginPlayerCommand.with(request.username(), request.password());
        final var output = loginPlayerUseCase.execute(command);
        return ResponseEntity.of(Optional.of(output));
    }
}
