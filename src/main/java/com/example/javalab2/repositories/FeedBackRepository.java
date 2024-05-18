package com.example.javalab2.repositories;

import com.example.javalab2.entities.Feedback;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findFeedbacksByUserId(Long id);

    List<Feedback> findFeedbacksByMovieId(Long id);

    List<Feedback> findFeedbacksByUserIdAndMovieId(Long movieId, Long userId);

    @Modifying
    @Query("DELETE FROM feedbacks WHERE user_id = :id")
    void deleteFeedbacksByUserId(Long id);

    @Modifying
    @Query("DELETE FROM feedbacks WHERE film_id = :id")
    void deleteFeedbacksByMovieId(Long id);

    @Modifying
    @Query("DELETE FROM feedbacks WHERE film_id = :movie_id AND user_id = :user_id")
    void deleteFeedbacksByUserIdAndMovieId(@Param("movie_id") Long movieId, @Param("user_id") Long userId);
}

