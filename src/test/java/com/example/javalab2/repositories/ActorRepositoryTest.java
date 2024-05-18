package com.example.javalab2.repositories;

import com.example.javalab2.entities.Actor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
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

    @Test
    void testThatConnectionEstablished() {
        assertThat(postgresSQLContainer.isCreated()).isTrue();
        assertThat(postgresSQLContainer.isRunning()).isTrue();
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
}