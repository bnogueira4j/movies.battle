package com.nogueira4j.movies.battle.infrastructure.bootstrap;

import com.nogueira4j.movies.battle.domain.movie.Movie;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.infrastructure.movie.persistence.MovieJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.movie.persistence.MovieRepository;
import com.nogueira4j.movies.battle.infrastructure.player.persistence.PlayerJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.player.persistence.PlayerRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class MovieWebScraping {
    static MovieRepository movieRepository;
    static PlayerRepository playerRepository;

    public MovieWebScraping(MovieRepository movieRepository, PlayerRepository playerRepository) {
        MovieWebScraping.movieRepository = movieRepository;
        MovieWebScraping.playerRepository = playerRepository;
    }

    public static void initializeMovies() throws IOException {
        if(playerRepository.count() <= 0) getPlayers();
        if(movieRepository.count() < 500) getMovies();
    }

    private static void getPlayers() {
        final var playerOne = Player.with(1L, "Ciclano", "ciclano123");
        final var playerTwo = Player.with(2L, "Fulano", "fulano123");
        playerRepository.save(PlayerJpaEntity.from(playerOne));
        playerRepository.save(PlayerJpaEntity.from(playerTwo));
    }
    private static Elements getGenreElements() throws IOException {
        final var url = "https://www.imdb.com/feature/genre/?ref_=nv_ch_gr";
        final var doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

        final var elementArticle = doc.getElementsByClass("article");
        final var elementArticleTable = elementArticle.get(5).getElementsByClass("table-cell primary");

        return elementArticleTable.select("a[href]");
    }

    private static void getMovies() throws IOException {
        final var genreElements = getGenreElements();

        for (var genreElement : genreElements) {

            Elements moviesElements = getMoviesElements(genreElement);
            moviesElements.forEach(movieElement -> {
                try {
                    final var movie = getMovieFields(movieElement);
                    movieRepository.save(MovieJpaEntity.from(movie));
                } catch (IndexOutOfBoundsException ignored) {

                }
            });
        }
    }

    private static Elements getMoviesElements(Element genreElement) throws IOException {
        final var genreAttr = genreElement.attr("href");
        final var url = "https://www.imdb.com" + genreAttr;
        final var genrePage = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

        return genrePage.getElementsByClass("lister-item mode-advanced");
    }

    private static Movie getMovieFields(Element movieElement) {
        final var title = Objects.requireNonNull(movieElement.getElementsByClass("lister-item-header").first()).child(1).text();
        final var votes = Objects.requireNonNull(movieElement.getElementsByClass("text-muted").get(3).parentNode()).childNode(3).attr("data-value");
        final var rate = movieElement.getElementsByClass("inline-block ratings-imdb-rating").attr("data-value");
        final var id = movieElement.getElementsByClass("ribbonize").attr("data-tconst");

        return Movie.with(
                id,
                title,
                Double.parseDouble(rate),
                Long.parseLong(votes)
        );
    }
}
