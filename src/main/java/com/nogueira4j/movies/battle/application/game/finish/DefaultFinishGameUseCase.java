package com.nogueira4j.movies.battle.application.game.finish;

import com.nogueira4j.movies.battle.application.game.create.CreateGameOutput;
import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.rank.Rank;
import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DefaultFinishGameUseCase extends FinishGameUseCase {
    private final GameGateway gameGateway;
    private final RankGateway rankGateway;

    public DefaultFinishGameUseCase(final GameGateway gameGateway, RankGateway rankGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
        this.rankGateway = Objects.requireNonNull(rankGateway);
    }

    @Override
    public FinishGameOutput execute(FinishGameCommand command) {
        final var game = gameGateway.findById(command.gameId());
        final var rank = Rank.with(game.getPlayerId(), game.getScore());
        rankGateway.save(rank);
        return FinishGameOutput.from(game.getPlayerId(), game.getId().toString(), rank.scored());
    }
}
