package com.nogueira4j.movies.battle.infrastructure.api.controllers;

import com.nogueira4j.movies.battle.application.game.create.CreateGameUseCase;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameCommand;
import com.nogueira4j.movies.battle.application.game.finish.FinishGameUseCase;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameCommand;
import com.nogueira4j.movies.battle.application.game.update.UpdateGameUseCase;
import com.nogueira4j.movies.battle.application.rank.get.GetRankUseCase;
import com.nogueira4j.movies.battle.infrastructure.api.GameAPI;
import com.nogueira4j.movies.battle.infrastructure.game.models.UpdateGameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
public class GameController implements GameAPI {

    private final CreateGameUseCase createGameUseCase;
    private final UpdateGameUseCase updateGameUseCase;
    private final FinishGameUseCase finishGameUseCase;
    private final GetRankUseCase getRankUseCase;

    public GameController(final CreateGameUseCase createGameUseCase, UpdateGameUseCase updateGameUseCase, FinishGameUseCase finishGameUseCase, GetRankUseCase getRankUseCase) {
        this.createGameUseCase = Objects.requireNonNull(createGameUseCase);
        this.updateGameUseCase = Objects.requireNonNull(updateGameUseCase);
        this.finishGameUseCase = Objects.requireNonNull(finishGameUseCase);
        this.getRankUseCase = getRankUseCase;
    }

    @Override
    public ResponseEntity<?> start() {
        final var output = createGameUseCase.execute();
        return ResponseEntity.of(Optional.of(output));
    }

    @Override
    public ResponseEntity<?> next(String gameId, UpdateGameRequest request) {
        final var command = UpdateGameCommand.with(gameId, request.winnerMovie(), request.loserMovie());
        final var output = updateGameUseCase.execute(command);
        return ResponseEntity.of(Optional.of(output));
    }

    @Override
    public ResponseEntity<?> finish(String gameId) {
        final var command = FinishGameCommand.with(gameId);
        final var output = finishGameUseCase.execute(command);
        return ResponseEntity.of(Optional.of(output));
    }

    @Override
    public ResponseEntity<?> rank() {
        return ResponseEntity.of(Optional.of(getRankUseCase.execute()));
    }
}
