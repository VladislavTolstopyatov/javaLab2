package com.example.javalab2.repository;

import com.example.javalab2.entity.Director;
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
public class DirectorRepositoryTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    void testThatConnectionEstablished() {
        assertThat(postgresSQLContainer.isCreated()).isTrue();
        assertThat(postgresSQLContainer.isRunning()).isTrue();
    }

    @BeforeEach
    void beforeEach() {
        directorRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        directorRepository.deleteAll();
    }

    @Test
    void saveDirectorTest() {
        Director director = directorRepository.save(new Director(null,
                "name",
                "surname,",
                "patronymic",
                LocalDate.of(2003, 3, 4),
                Boolean.TRUE, Collections.emptyList()));

        assertThat(director).isEqualTo(directorRepository.findById(director.getId()).get());
    }

    @Test
    void deleteDirectorByIdTest() {
        Director director = directorRepository.save(new Director(null,
                "name",
                "surname,",
                "patronymic",
                LocalDate.of(2003, 3, 4),
                Boolean.TRUE, Collections.emptyList()));

        directorRepository.deleteById(director.getId());
        assertThat(directorRepository.findById(director.getId()).isEmpty()).isTrue();
    }

    @Test
    void findAllDirectorsTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        List<Director> directors = (List<Director>) directorRepository.findAll();
        assertTrue(directors.size() == directorList.size() &&
                directors.containsAll(directorList) && directorList.containsAll(directors));
    }

    @Test
    void deleteAllDirectorsTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        directorRepository.deleteAll();
        List<Director> directors = (List<Director>) directorRepository.findAll();
        assertThat(directors.isEmpty()).isTrue();
    }

    @Test
    void findDirectorsByNameTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        List<Director> directors = directorRepository.findDirectorsByName(directorList.get(0).getName());
        assertTrue(directors.get(0).equals(directorList.get(0)) &&
                directors.size() == 1);
    }

    @Test
    void findDirectorsBySurnameTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        List<Director> directors = directorRepository.findDirectorsBySurname(directorList.get(0).getSurname());
        assertTrue(directors.get(0).equals(directorList.get(0)) &&
                directors.size() == 1);
    }

    @Test
    void findDirectorsByPatronymicTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        List<Director> directors = directorRepository.findDirectorsByPatronymic(directorList.get(0).getPatronymic());
        assertTrue(directors.get(0).equals(directorList.get(0)) &&
                directors.size() == 1);
    }

    @Test
    void findDirectorsByBirthDateTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 5),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        List<Director> directors = directorRepository.findDirectorsByBirthdate(directorList.get(0).getBirthdate());
        assertTrue(directors.get(0).equals(directorList.get(0)) &&
                directors.size() == 1);
    }

    @Test
    void findDirectorsByOscarTest() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));

        directorRepository.saveAll(directorList);
        List<Director> directors = directorRepository.findDirectorsByOscar(directorList.get(0).getOscar());
        assertTrue(directors.get(0).equals(directorList.get(0)) &&
                directors.size() == 1);
    }

    @Test
    void deleteDirectorByNameAndSurnameAndPatronymic() {
        List<Director> directorList = List.of(new Director(null,
                        "name",
                        "surname",
                        "patronymic",
                        LocalDate.of(2003, 3, 4),
                        Boolean.TRUE, Collections.emptyList()),
                new Director(null,
                        "name1",
                        "surname1",
                        "patronymic1",
                        LocalDate.of(2003, 3, 4),
                        Boolean.FALSE, Collections.emptyList()));
        directorRepository.saveAll(directorList);
        directorRepository.deleteDirectorByNameAndSurnameAndPatronymic(directorList.get(0).getName(),
                directorList.get(0).getSurname(), directorList.get(0).getPatronymic());
        List<Director> directors = directorRepository.findAll();
        assertTrue(directors.get(0).equals(directorList.get(1)) &&
                directors.size() == 1);
    }

    @Test
    void updateDirectorTest() {
        Director director = directorRepository.save(new Director(null,
                "name",
                "surname,",
                "patronymic",
                LocalDate.of(2003, 3, 4),
                Boolean.FALSE, Collections.emptyList()));
        director.setOscar(Boolean.TRUE);
        Director updatedDirector = directorRepository.save(director);
        assertThat(director).isEqualTo(director).isEqualTo(updatedDirector);
    }
}