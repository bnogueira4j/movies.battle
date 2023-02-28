package com.nogueira4j.movies.battle.application.rank.get;

import com.nogueira4j.movies.battle.application.UserCaseTest;
import com.nogueira4j.movies.battle.domain.rank.Rank;
import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class GetRankUseCaseTest extends UserCaseTest {

    @InjectMocks
    private DefaultGetRankUseCase useCase;
    @Mock
    private RankGateway rankGateway;

    @Test
    public void givenGetAllRank_whenCallsGetRank_shouldReturnRankList() {
        // given
        final var expectedRankOne = Rank.with("player1", 100);
        final var expectedRankTwo = Rank.with("player2", 89);
        final var expectedRankThree = Rank.with("player3", 70);

        when(rankGateway.findAll()).thenReturn(
                List.of(
                        Rank.with("player1", 100),
                        Rank.with("player2", 89),
                        Rank.with("player3", 70)
                )
        );

        // when
        final var actualOutput = useCase.execute();

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(3, actualOutput.rank().size());
        Assertions.assertEquals(expectedRankOne.playerId(), actualOutput.rank().get(0).playerId());
        Assertions.assertEquals(expectedRankOne.scored(), actualOutput.rank().get(0).scored());

        Assertions.assertEquals(expectedRankTwo.playerId(), actualOutput.rank().get(1).playerId());
        Assertions.assertEquals(expectedRankTwo.scored(), actualOutput.rank().get(1).scored());

        Assertions.assertEquals(expectedRankThree.playerId(), actualOutput.rank().get(2).playerId());
        Assertions.assertEquals(expectedRankThree.scored(), actualOutput.rank().get(2).scored());

        Mockito.verify(rankGateway, times(1)).findAll();
    }
}
