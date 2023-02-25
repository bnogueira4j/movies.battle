package com.nogueira4j.movies.battle.infrastructure.game;

import com.nogueira4j.movies.battle.domain.game.Movie;
import com.nogueira4j.movies.battle.domain.game.MovieGateway;
import com.nogueira4j.movies.battle.infrastructure.game.persistence.MovieJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.game.persistence.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class MovieGatewayH2 implements MovieGateway {
    private final MovieRepository movieRepository;

    public MovieGatewayH2(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id).map(MovieJpaEntity::toAggregate);
    }

    @Override
    public List<Movie> find2MovieByRandom() {
        return movieRepository.find2MovieByRandom().stream().map(MovieJpaEntity::toAggregate).toList();
    }
}
