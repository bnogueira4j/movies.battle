package com.nogueira4j.movies.battle.infrastructure.movie;

import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.movie.MovieGateway;
import com.nogueira4j.movies.battle.infrastructure.movie.persistence.MovieJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.movie.persistence.MovieRepository;
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
