package com.nogueira4j.movies.battle.infrastructure.game.persistence;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieJpaEntity, String> {

    @Query(nativeQuery=true, value="SELECT *  FROM movie ORDER BY random() LIMIT 2")
    List<MovieJpaEntity> find2MovieByRandom();
}
