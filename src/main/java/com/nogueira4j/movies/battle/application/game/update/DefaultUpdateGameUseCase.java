package com.nogueira4j.movies.battle.application.game.update;

import com.nogueira4j.movies.battle.domain.exceptions.DomainException;
import com.nogueira4j.movies.battle.domain.exceptions.NotFoundException;
import com.nogueira4j.movies.battle.domain.exceptions.RoundAlreadyExistsException;
import com.nogueira4j.movies.battle.domain.exceptions.ScoreErrorsExceededException;
import com.nogueira4j.movies.battle.domain.game.Game;
import com.nogueira4j.movies.battle.domain.game.GameGateway;
import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.movie.MovieGateway;
import com.nogueira4j.movies.battle.domain.game.Round;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.domain.player.PlayerGateway;
import com.nogueira4j.movies.battle.domain.rank.Rank;
import com.nogueira4j.movies.battle.domain.rank.RankGateway;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DefaultUpdateGameUseCase extends UpdateGameUseCase {
    private final GameGateway gameGateway;
    private final MovieGateway movieGateway;
    private final RankGateway rankGateway;
    private final PlayerGateway playerGateway;

    public DefaultUpdateGameUseCase(
            final GameGateway gameGateway,
            final MovieGateway movieGateway,
            final RankGateway rankGateway,
            final PlayerGateway playerGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
        this.movieGateway = Objects.requireNonNull(movieGateway);
        this.rankGateway = Objects.requireNonNull(rankGateway);
        this.playerGateway = Objects.requireNonNull(playerGateway);
    }

    @Override
    public UpdateGameOutput execute(UpdateGameCommand updateGameCommand) {
        final var game = gameGateway.findById(updateGameCommand.gameId())
                .orElseThrow(() -> NotFoundException.with(Game.class, updateGameCommand.gameId()));
        final var winningMovie = movieGateway.findById(updateGameCommand.winningMovie())
                .orElseThrow(() -> NotFoundException.with(Movie.class, updateGameCommand.winningMovie()));
        final var loserMovie = movieGateway.findById(updateGameCommand.loserMovie())
                .orElseThrow(() -> NotFoundException.with(Movie.class, updateGameCommand.loserMovie()));

        updateGameStatus(game, winningMovie, loserMovie);

        final var newRound = generateRound(game);
        game.addRound(newRound);

        gameGateway.update(game);
        return UpdateGameOutput.from(newRound);
    }

    private void updateGameStatus(Game game, Movie winningMovie, Movie loserMovie) {
        try{
            finishRound(game);
            game.updateScore(winningMovie.isWinner(loserMovie));
        } catch(ScoreErrorsExceededException ex) {
            finishGame(game);
            throw ex;
        }
    }

    private static void finishRound(Game game) {
        try {
            Round round = game.getRounds().stream().filter(r -> Round.StatusRound.IN_PROGRESS.equals(r.getStatusRound())).toList().get(0);
            round.finish();
        } catch (IndexOutOfBoundsException ex) {
            throw NotFoundException.with(Round.class, Round.StatusRound.IN_PROGRESS.name());
        }
    }

    private void finishGame(Game game) {
        final var username = SecurityContextHolder.getContext().getAuthentication().getName();
        final var player = playerGateway.findByUsername(username).orElseThrow(() -> NotFoundException.with(Player.class, username));
        gameGateway.update(game);
        rankGateway.save(Rank.with(player.id().toString(), game.getScore()));
    }

    private Round generateRound(Game game) {
        try {
             final var round = gameGateway.createRound();
             game.validateRound(round);
             return round;
        } catch (RoundAlreadyExistsException e){
            return generateRound(game);
        } catch (StackOverflowError e) { throw new DomainException(
                "We are having technical problems generating a new match, please try again later");
        }
    }
}
