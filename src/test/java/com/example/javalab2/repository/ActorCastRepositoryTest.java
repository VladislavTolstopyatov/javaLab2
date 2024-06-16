package com.example.javalab2.repository;

import com.example.javalab2.entity.*;
import com.example.javalab2.entity.enums.Genre;
import org.junit.jupiter.api.AfterEach;
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
public class ActorCastRepositoryTest {
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private ActorCastRepository actorCastRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    private Movie movie;

    private Actor actor;

    private Director director;

    @Test
    void testThatConnectionEstablished() {
        assertThat(postgresSQLContainer.isCreated()).isTrue();
        assertThat(postgresSQLContainer.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        director = directorRepository.save(new Director(null,
                "test",
                "test",
                "test",
                LocalDate.of(2003, 3, 3),
                Boolean.TRUE,
                Collections.emptyList()));

        movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()
        ));

        actor = actorRepository.save(new Actor(null,
                "test",
                LocalDate.of(2003, 3, 3)
        ));
    }

    @BeforeEach
    void beforeEach() {
        directorRepository.deleteById(director.getId());
        actorRepository.deleteById(actor.getId());
    }

    @AfterEach
    void tearDown() {
        directorRepository.deleteById(director.getId());
        actorRepository.deleteById(actor.getId());
    }

    @Test
    void saveActorCastTest() {
        ActorsCast actorsCast = actorCastRepository.save(new ActorsCast(null,
                actor,
                movie));
        assertThat(actorCastRepository.findById(actorsCast.getId()).get()).isEqualTo(actorsCast);
    }


    @Test
    void deleteActorCastByIdTest() {
        ActorsCast actorsCast = actorCastRepository.save(new ActorsCast(null,
                actor,
                movie));
        actorCastRepository.deleteById(actorsCast.getId());
        assertThat(actorCastRepository.findById(actorsCast.getId())).isEmpty();
    }

    @Test
    void findAllActorCastTest() {
        List<ActorsCast> actorsCastList = List.of(new ActorsCast(null,
                        actor,
                        movie),
                new ActorsCast(null,
                        actor,
                        movie));
        actorCastRepository.saveAll(actorsCastList);

        List<ActorsCast> actorsCasts = actorCastRepository.findAll();
        assertTrue(actorsCasts.size() == actorsCastList.size() &&
                actorsCasts.containsAll(actorsCastList) && actorsCastList.containsAll(actorsCasts));
    }

    @Test
    void findAllActorCastsByActorIdTest() {
        List<ActorsCast> actorsCastList = List.of(new ActorsCast(null,
                        actor,
                        movie),
                new ActorsCast(null,
                        actor,
                        movie));
        actorCastRepository.saveAll(actorsCastList);

        List<ActorsCast> actorsCasts = actorCastRepository.findActorsCastsByActorId(actor.getId());
        assertTrue(actorsCasts.size() == actorsCastList.size() &&
                actorsCasts.containsAll(actorsCastList) && actorsCastList.containsAll(actorsCasts));
    }

    @Test
    void findAllActorCastsByMovieIdTest() {
        List<ActorsCast> actorsCastList = List.of(new ActorsCast(null,
                        actor,
                        movie),
                new ActorsCast(null,
                        actor,
                        movie));
        actorCastRepository.saveAll(actorsCastList);

        List<ActorsCast> actorsCasts = actorCastRepository.findActorsCastsByMovieId(movie.getId());
        assertTrue(actorsCasts.size() == actorsCastList.size() &&
                actorsCasts.containsAll(actorsCastList) && actorsCastList.containsAll(actorsCasts));
    }

    @Test
    void findAllActorCastsByMovieIdAndActorIdTest() {
        List<ActorsCast> actorsCastList = List.of(new ActorsCast(null,
                        actor,
                        movie),
                new ActorsCast(null,
                        actor,
                        movie));
        actorCastRepository.saveAll(actorsCastList);

        List<ActorsCast> actorsCasts = actorCastRepository.findActorsCastsByMovieIdAndMovieId(movie.getId(), actor.getId());
        assertTrue(actorsCasts.size() == actorsCastList.size() &&
                actorsCasts.containsAll(actorsCastList) && actorsCastList.containsAll(actorsCasts));
    }

    @Test
    void deleteActorCastByActorIdTest() {
        ActorsCast actorsCast = actorCastRepository.save(new ActorsCast(null,
                actor,
                movie));
        actorCastRepository.deleteActorsCastByActorId(actor.getId());
        assertThat(actorCastRepository.findActorsCastsByActorId(actor.getId()).isEmpty()).isTrue();
    }

    @Test
    void deleteActorCastByMovieIdTest() {
        ActorsCast actorsCast = actorCastRepository.save(new ActorsCast(null,
                actor,
                movie));
        actorCastRepository.deleteActorsCastByMovieId(movie.getId());
        assertThat(actorCastRepository.findActorsCastsByMovieId(actor.getId()).isEmpty()).isTrue();
    }

    @Test
    void deleteActorCastByMovieIdAndActorIdTest() {
        ActorsCast actorsCast = actorCastRepository.save(new ActorsCast(null,
                actor,
                movie));
        actorCastRepository.deleteActorsCastByActorIdAndMovieId(movie.getId(), actor.getId());
        assertThat(actorCastRepository.findActorsCastsByMovieIdAndMovieId(movie.getId(), actor.getId()).isEmpty()).isTrue();
    }
}