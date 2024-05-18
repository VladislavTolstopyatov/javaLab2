package com.example.javalab2.repositories;

import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.enums.Genre;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findMovieByTitle(String title);

    Movie findMovieByDescription(String title);

    List<Movie> findMoviesByDateOfRelease(LocalDate date);

    List<Movie> findMoviesByGenre(Genre genre);

    List<Movie> findMoviesByDirectorId(Long id);

    List<Movie> findMoviesByDuration(Integer duration);

    @Modifying
    @Query("DELETE FROM movies WHERE movies.title LIKE :title")
    void deleteByTitle(@Param("title") String title);

    @Modifying
    @Query("DELETE FROM movies WHERE movies.director_id = :id")
    void deleteMoviesByDirectorId(Long id);
}
