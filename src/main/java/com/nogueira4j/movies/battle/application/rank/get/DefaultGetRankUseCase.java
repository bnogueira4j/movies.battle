package com.nogueira4j.movies.battle.application.rank.get;


import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class DefaultGetRankUseCase extends GetRankUseCase {
    private final RankGateway rankGateway;

    public DefaultGetRankUseCase(final RankGateway rankGateway) {
        this.rankGateway = Objects.requireNonNull(rankGateway);
    }

    @Override
    public GetRankOutput execute() {
        return GetRankOutput.from(rankGateway.findAll());
    }
}
