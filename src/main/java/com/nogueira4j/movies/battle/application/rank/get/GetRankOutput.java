package com.nogueira4j.movies.battle.application.rank.get;

import com.nogueira4j.movies.battle.domain.rank.Rank;

import java.util.List;

public record GetRankOutput(List<Rank> rank){
    public static GetRankOutput from(
            final List<Rank> rank
    ) {
        return new GetRankOutput(rank);
    }
}
