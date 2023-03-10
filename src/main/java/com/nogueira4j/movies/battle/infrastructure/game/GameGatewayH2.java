package com.nogueira4j.movies.battle.infrastructure.game;

import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.movie.MovieGateway;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.infrastructure.game.persistence.GameJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.game.persistence.GameRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GameGatewayH2 implements GameGateway {
    private final GameRepository gameRepository;
    private final MovieGateway movieGateway;

    public GameGatewayH2(GameRepository gameRepository, MovieGateway movieGateway) {
        this.gameRepository = gameRepository;
        this.movieGateway = movieGateway;
    }

    @Override
    public Game create(Game game) {
        final var gameEntity = GameJpaEntity.from(game);
        return gameRepository.save(gameEntity).toAggregate();
    }

    @Override
    public Game update(Game game) {
        final var gameEntity = GameJpaEntity.from(game);
        return gameRepository.save(gameEntity).toAggregate();
    }

    @Override
    public Optional<Game> findById(String id) {
        final var game = gameRepository.findById(id);
            return game.map(GameJpaEntity::toAggregate);
    }

    @Override
    public Round createRound() {
        final var movies = movieGateway.find2MovieByRandom();
        return Round.with(movies.get(0), movies.get(1));
    }
}
