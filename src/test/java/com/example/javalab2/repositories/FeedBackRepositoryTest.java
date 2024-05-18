package com.example.javalab2.repositories;

import com.example.javalab2.entities.Director;
import com.example.javalab2.entities.Feedback;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Genre;
import com.example.javalab2.entities.enums.Role;
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
public class FeedBackRepositoryTest {
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DirectorRepository directorRepository;

    private User user;

    private Movie movie;

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

        user = userRepository.save(new User(null,
                "test",
                "test",
                "test",
                Role.USER,
                Collections.emptyList()));

        movie = movieRepository.save(new Movie(null,
                "test",
                "test",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList()));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(user.getId());
        directorRepository.deleteById(director.getId());
    }

    @Test
    void saveFeedBackTest() {
        Feedback feedback = feedBackRepository.save(new Feedback(null,
                movie,
                user,
                LocalDate.of(2003, 3, 3),
                "test"));
        assertThat(feedBackRepository.findById(feedback.getId()).get()).isEqualTo(feedback);
    }

    @Test
    void updateFeedBackTest() {
        Feedback feedback = feedBackRepository.save(new Feedback(null,
                movie,
                user,
                LocalDate.of(2003, 3, 3),
                "test"));

        feedback.setFeedback("new");
        Feedback feedbackNew = feedBackRepository.save(feedback);
        assertThat(feedBackRepository.findById(feedback.getId()).get()).isEqualTo(feedbackNew);
    }

    @Test
    void deleteFeedBackByIdTest() {
        Feedback feedback = feedBackRepository.save(new Feedback(null,
                movie,
                user,
                LocalDate.of(2003, 3, 3),
                "test"));

        feedBackRepository.deleteById(feedback.getId());
        assertThat(feedBackRepository.findById(feedback.getId()).isEmpty()).isTrue();
    }

    @Test
    void findAllFeedBacksTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        List<Feedback> feedbacks = (List<Feedback>) feedBackRepository.findAll();
        assertTrue(feedbacks.size() == feedbackList.size() &&
                feedbacks.containsAll(feedbackList) && feedbackList.containsAll(feedbacks));
    }

    @Test
    void deleteAllFeedBacksTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        feedBackRepository.deleteAll();
        List<Feedback> feedbacks = (List<Feedback>) feedBackRepository.findAll();
        assertThat(feedbacks.isEmpty()).isTrue();
    }

    @Test
    void findAllFeedBacksByUserIdTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        List<Feedback> feedbacks = feedBackRepository.findFeedbacksByUserId(user.getId());
        assertTrue(feedbacks.size() == feedbackList.size() &&
                feedbacks.containsAll(feedbackList) && feedbackList.containsAll(feedbacks));
    }

    @Test
    void findAllFeedBacksByMovieIdTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        List<Feedback> feedbacks = feedBackRepository.findFeedbacksByMovieId(movie.getId());
        assertTrue(feedbacks.size() == feedbackList.size() &&
                feedbacks.containsAll(feedbackList) && feedbackList.containsAll(feedbacks));
    }

    @Test
    void findAllFeedBacksByMovieIdAndUserIdTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        List<Feedback> feedbacks = feedBackRepository.findFeedbacksByUserIdAndMovieId(movie.getId(), user.getId());
        assertTrue(feedbacks.size() == feedbackList.size() &&
                feedbacks.containsAll(feedbackList) && feedbackList.containsAll(feedbacks));
    }

    @Test
    void deleteAllFeedBacksByUserIdTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        feedBackRepository.deleteFeedbacksByUserId(user.getId());
        List<Feedback> feedbacks = feedBackRepository.findFeedbacksByUserId(user.getId());
        assertThat(feedbacks.isEmpty()).isTrue();
    }

    @Test
    void deleteAllFeedBacksByMovieIdTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        feedBackRepository.deleteFeedbacksByMovieId(user.getId());
        List<Feedback> feedbacks = feedBackRepository.findFeedbacksByMovieId(movie.getId());
        assertThat(feedbacks.isEmpty()).isTrue();
    }

    @Test
    void deleteAllFeedBacksByUserIdAndMovieIdTest() {
        List<Feedback> feedbackList = List.of(
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test"),
                new Feedback(null,
                        movie,
                        user,
                        LocalDate.of(2003, 3, 3),
                        "test")
        );
        feedBackRepository.saveAll(feedbackList);
        feedBackRepository.deleteFeedbacksByUserIdAndMovieId(movie.getId(), user.getId());
        List<Feedback> feedbacks = feedBackRepository.findFeedbacksByUserIdAndMovieId(movie.getId(), user.getId());
        assertThat(feedbacks.isEmpty()).isTrue();
    }
}