package com.nogueira4j.movies.battle.infrastructure.bootstrap;

import com.nogueira4j.movies.battle.domain.game.Movie;
import com.nogueira4j.movies.battle.domain.game.MovieGateway;
import com.nogueira4j.movies.battle.domain.player.Player;
import com.nogueira4j.movies.battle.infrastructure.game.persistence.MovieJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.game.persistence.MovieRepository;
import com.nogueira4j.movies.battle.infrastructure.player.persistence.PlayerJpaEntity;
import com.nogueira4j.movies.battle.infrastructure.player.persistence.PlayerRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if(movieRepository.count() < 675) getMovies();
    }

    private static void getPlayers() {
        final var playerOne = Player.with(1L, "Ciclano", "ciclano123");
        final var playerTwo = Player.with(2L, "Fulano", "fulano123");
        playerRepository.save(PlayerJpaEntity.from(playerOne));
        playerRepository.save(PlayerJpaEntity.from(playerTwo));
    }
    private static Elements getGenre() throws IOException {
        String url = "https://www.imdb.com/feature/genre/?ref_=nv_ch_gr";
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

        Elements links = doc.getElementsByClass("article");
        Elements links2 = links.get(5).getElementsByClass("table-cell primary");

        return links2.select("a[href]");
    }

    private static void getMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        Elements links = getGenre();

        for (Element link : links) {

            String urlGenre = link.attr("href");
            String url = "https://www.imdb.com" + urlGenre;
            Document genrePage = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

            Elements moviesLinks = genrePage.getElementsByClass("lister-item mode-advanced");
            for (Element movieNode : moviesLinks) {
                try {
                    String title = movieNode.getElementsByClass("lister-item-header").first().child(1).text();
                    String votes = movieNode.getElementsByClass("text-muted").get(3).parentNode().childNode(3).attr("data-value");
                    String rate = movieNode.getElementsByClass("inline-block ratings-imdb-rating").attr("data-value");
                    String idMovie = movieNode.getElementsByClass("ribbonize").attr("data-tconst");

                    final var movie = Movie.with(
                            idMovie,
                            title,
                            Double.parseDouble(rate),
                            Long.parseLong(votes)
                    );

                    movieRepository.save(MovieJpaEntity.from(movie));
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
    }
}
