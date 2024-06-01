package com.example.javalab2.repositories;

import com.example.javalab2.entities.Actor;
import com.example.javalab2.entities.ActorsCast;
import com.example.javalab2.entities.Director;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.enums.Genre;
import org.junit.Before;
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
public class ActorRepositoryTest {
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorCastRepository actorCastRepository;

    @Test
    void testThatConnectionEstablished() {
        assertThat(postgresSQLContainer.isCreated()).isTrue();
        assertThat(postgresSQLContainer.isRunning()).isTrue();
    }

    @BeforeEach
    void beforeEach() {
        actorRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        actorRepository.deleteAll();
    }

    @Test
    void saveActorTest() {
        Actor actor = actorRepository.save(new Actor(null,
                "test", LocalDate.of(2003, 3, 3)));

        Actor actorFromRepo = actorRepository.findById(actor.getId()).get();
        assertThat(actor).isEqualTo(actorFromRepo);
    }

    @Test
    void findAllActorsTest() {
        List<Actor> actorList = List.of(new Actor(null,
                        "test",
                        LocalDate.of(2003, 4, 3)),
                new Actor(null,
                        "testing",
                        LocalDate.of(2003, 4, 3)));
        actorRepository.saveAll(actorList);

        List<Actor> actors = actorRepository.findAll();
        assertTrue(actors.size() == actorList.size() &&
                actors.containsAll(actorList) && actorList.containsAll(actors));
    }

    @Test
    void deleteActorById() {
        Actor actor = actorRepository.save(new Actor(null,
                "test", LocalDate.of(2003, 4, 3)));

        Actor actorFromRepo = actorRepository.findById(actor.getId()).get();
        actorRepository.deleteById(actorFromRepo.getId());
        assertThat(actorRepository.findById(actorFromRepo.getId())).isEmpty();
    }

    @Test
    void deleteAllActorsTest() {
        List<Actor> actorList = List.of(new Actor(null,
                        "test",
                        LocalDate.of(2003, 4, 3)),
                new Actor(null,
                        "testing",
                        LocalDate.of(2003, 4, 3)));
        actorRepository.saveAll(actorList);
        actorRepository.deleteAll();
        List<Actor> actors = actorRepository.findAll();
        assertThat(actors.isEmpty()).isTrue();
    }

    @Test
    void findActorByFio() {
        Actor actor = actorRepository.save(new Actor(null,
                "test", LocalDate.of(2003, 4, 3)));

        Actor actorFromRepo = actorRepository.findActorByFio(actor.getFio());
        assertThat(actor).isEqualTo(actorFromRepo);
    }

    @Test
    void findActorsByBirthDate() {
        List<Actor> actorList = List.of(new Actor(null,
                        "test",
                        LocalDate.of(2003, 4, 3)),
                new Actor(null,
                        "testing",
                        LocalDate.of(2003, 4, 3)));
        actorRepository.saveAll(actorList);

        List<Actor> actors = actorRepository.findActorsByBirthdate(actorList.get(0).getBirthdate());
        assertTrue(actors.size() == actorList.size() &&
                actors.containsAll(actorList) && actorList.containsAll(actors));
    }

    @Test
    void deleteActorByFio() {
        Actor actor = actorRepository.save(new Actor(null,
                "test", LocalDate.of(2003, 4, 3)));

        Actor actorFromRepo = actorRepository.findActorByFio(actor.getFio());
        actorRepository.deleteActorByFio(actor.getFio());
        assertThat(actorRepository.findActorByFio(actor.getFio())).isNull();
    }

    @Test
    void updateActorTest() {
        Actor actor = actorRepository.save(new Actor(null,
                "test", LocalDate.of(2003, 4, 3)));
        actor.setFio("new");
        actor = actorRepository.save(actor);
        assertThat(actor).isEqualTo(actorRepository.findActorByFio(actor.getFio()));
    }

    @Test
    void findAllActorsByMovieIdTest() {
        Director director = directorRepository.save(new Director(null,
                "test",
                "test",
                "test",
                LocalDate.of(2003, 3, 3),
                Boolean.TRUE,
                Collections.emptyList()));

        Movie movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList()
        ));

        Actor actor = actorRepository.save(new Actor(null,
                "test",
                LocalDate.of(2003, 3, 3)
        ));

        ActorsCast actorsCast = actorCastRepository.save(new ActorsCast(null,
                actor,
                movie));

        List<Actor> actors = List.of(actor);
        List<Actor> actorListFromRepository = actorRepository.findActorsByMovieId(movie.getId());
        assertThat(actors.get(0).equals(actorListFromRepository.get(0))
                && actors.size() == actorListFromRepository.size()).isTrue();
    }
}