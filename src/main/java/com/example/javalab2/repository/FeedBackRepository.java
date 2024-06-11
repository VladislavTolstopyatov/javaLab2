package com.example.javalab2.repository;

import com.example.javalab2.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findFeedbacksByUserId(Long id);

    List<Feedback> findFeedbacksByMovieId(Long id);

    List<Feedback> findFeedbacksByUserIdAndMovieId(Long userId, Long movieId);

    @Modifying
    @Query(value = "DELETE FROM feedbacks WHERE user_id = :id", nativeQuery = true)
    void deleteFeedbacksByUserId(Long id);

    @Modifying
    @Query(value = "DELETE FROM feedbacks WHERE film_id = :id", nativeQuery = true)
    void deleteFeedbacksByMovieId(Long id);

    @Modifying
    @Query(value = "DELETE FROM feedbacks WHERE film_id = :movie_id AND user_id = :user_id", nativeQuery = true)
    void deleteFeedbacksByUserIdAndMovieId(@Param("movie_id") Long movieId, @Param("user_id") Long userId);
}

