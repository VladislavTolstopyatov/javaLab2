package com.example.javalab2.repositories;

import com.example.javalab2.entities.ActorsCast;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorCastRepository extends JpaRepository<ActorsCast, Long> {
    List<ActorsCast> findActorsCastsByActorId(Long id);

    List<ActorsCast> findActorsCastsByMovieId(Long id);

    List<ActorsCast> findActorsCastsByMovieIdAndMovieId(Long movieId, Long actorId);

    @Modifying
    @Query("DELETE FROM actors_cast WHERE actors_cast.actor_id = :id")
    void deleteActorsCastByActorId(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM actors_cast WHERE film_id = :id")
    void deleteActorsCastByMovieId(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM actors_cast WHERE film_id = :movie_id AND actor_id = :actor_id")
    void deleteActorsCastByActorIdAndMovieId(@Param("movie_id") Long movieId, @Param("actor_id") Long actorId);
}
