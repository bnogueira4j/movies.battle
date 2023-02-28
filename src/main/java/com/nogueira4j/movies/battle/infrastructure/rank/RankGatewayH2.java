package com.nogueira4j.movies.battle.infrastructure.rank;

import com.nogueira4j.movies.battle.domain.rank.Rank;
import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import com.nogueira4j.movies.battle.infrastructure.rank.persistence.RankJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.rank.persistence.RankRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RankGatewayH2 implements RankGateway {
    private final RankRepository rankRepository;

    public RankGatewayH2(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Override
    public Rank save(Rank rank) {
        return rankRepository.save(RankJpaEntity.from(rank)).toAggregate();
    }

    @Override
    public List<Rank> findAll() {
        return rankRepository.findAllByOrderByScoredDesc().stream().map(RankJpaEntity::toAggregate).toList();
    }
}
