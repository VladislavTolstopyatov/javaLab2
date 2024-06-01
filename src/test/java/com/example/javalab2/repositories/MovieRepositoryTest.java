package com.example.javalab2.repositories;

import com.example.javalab2.entities.Director;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.enums.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    private Director director;

    @Test
    void testThatConnectionEstablished() {
        assertThat(postgresSQLContainer.isCreated()).isTrue();
        assertThat(postgresSQLContainer.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
        director = directorRepository.save(new Director(null,
                "test",
                "test",
                "test",
                LocalDate.of(2003, 3, 3),
                Boolean.TRUE,
                Collections.emptyList()));
    }

    @AfterEach
    void tearDown() {
        directorRepository.deleteById(director.getId());
    }

    @Test
    void createMovieTest() {
        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()));

        assertThat(movie).isEqualTo(movieRepository.findById(movie.getId()).get());
    }

    @Test
    void deleteMovieByIdTest() {
        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()));
        movieRepository.deleteById(movie.getId());
        assertThat(movieRepository.findById(movie.getId())).isEmpty();
    }

    @Test
    void updateMovieTest() {
        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()));

        movie.setDuration(130);
        Movie updatedMovie = movieRepository.save(movie);
        assertThat(updatedMovie).isEqualTo(movie);
    }

    @Test
    void findAllMoviesTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);

        List<Movie> moviesFromRepo = movieRepository.findAll();
        assertTrue(movieList.size() == moviesFromRepo.size() &&
                movieList.containsAll(moviesFromRepo) && moviesFromRepo.containsAll(movieList));
    }

    @Test
    void deleteAllMoviesTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);
        movieRepository.deleteAll();
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
        assertThat(movies.isEmpty()).isTrue();
    }

    @Test
    void findMovieByTitleTest() {
        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()));

        Movie movieFromRepo = movieRepository.findMovieByTitle(movie.getTitle());
        assertThat(movie).isEqualTo(movieFromRepo);
    }

    @Test
    void findMovieByDescriptionTest() {
        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()));

        Movie movieFromRepo = movieRepository.findMovieByDescription(movie.getDescription());
        assertThat(movie).isEqualTo(movieFromRepo);
    }

    @Test
    void findMoviesByDateOfReleaseTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);
        List<Movie> movies = movieRepository.findMoviesByDateOfRelease(movieList.get(0).getDateOfRelease());
        assertTrue(movieList.size() == movies.size() &&
                movieList.containsAll(movies) && movies.containsAll(movieList));
    }

    @Test
    void findMoviesByGenreTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);
        List<Movie> movies = movieRepository.findMoviesByGenre(movieList.get(0).getGenre());
        assertTrue(movieList.size() == movies.size() &&
                movieList.containsAll(movies) && movies.containsAll(movieList));
    }

    @Test
    void findMoviesByDirectorIdTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);
        List<Movie> movies = movieRepository.findMoviesByDirectorId(movieList.get(0).getDirector().getId());
        assertTrue(movieList.size() == movies.size() &&
                movieList.containsAll(movies) && movies.containsAll(movieList));
    }

    @Test
    void findMoviesByDurationTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);
        List<Movie> movies = movieRepository.findMoviesByDuration(movieList.get(0).getDuration());
        assertTrue(movieList.size() == movies.size() &&
                movieList.containsAll(movies) && movies.containsAll(movieList));
    }

    @Test
    void deleteMovieByTitleTest() {
        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()));
        movieRepository.deleteByTitle(movie.getTitle());
        assertThat(movieRepository.findMovieByTitle(movie.getTitle())).isNull();
    }

    @Test
    void deleteMoviesByDirectorIdTest() {
        List<Movie> movieList = List.of(
                new Movie(null,
                        "test",
                        "test",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()),
                new Movie(null,
                        "test1",
                        "test1",
                        Genre.COMEDY,
                        LocalDate.of(2003, 3, 3),
                        150,
                        director,
                        Collections.emptyList(),
                        Collections.emptyList()));

        movieRepository.saveAll(movieList);
        movieRepository.deleteMoviesByDirectorId(movieList.get(0).getDirector().getId());
        List<Movie> movies = movieRepository.findMoviesByDirectorId(movieList.get(0).getDirector().getId());
        assertThat(movies.isEmpty()).isTrue();
    }
}