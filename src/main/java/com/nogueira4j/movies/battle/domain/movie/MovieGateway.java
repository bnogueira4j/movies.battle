package com.nogueira4j.movies.battle.domain.movie;

import java.util.List;
import java.util.Optional;

public interface MovieGateway {
    Optional<Movie> findById(String id);
    List<Movie> find2MovieByRandom();
}
