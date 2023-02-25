package com.nogueira4j.movies.battle.domain.rank;


import java.util.List;

public interface RankGateway {
    Rank save(Rank rank);
    List<Rank> findAll();
}
