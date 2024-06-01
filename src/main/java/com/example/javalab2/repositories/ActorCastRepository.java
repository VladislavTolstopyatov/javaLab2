package com.example.javalab2.repositories;

import com.example.javalab2.entities.ActorsCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorCastRepository extends JpaRepository<ActorsCast, Long> {
    List<ActorsCast> findActorsCastsByActorId(Long id);

    List<ActorsCast> findActorsCastsByMovieId(Long id);

    List<ActorsCast> findActorsCastsByMovieIdAndMovieId(Long movieId, Long actorId);

    List<ActorsCast> findActorsCastsByMovie_Title(String title);

    List<ActorsCast> findActorsCastsByActor_Fio(String fio);

    List<ActorsCast> findActorsCastsByActor_FioAndMovie_Title(String fio, String title);

    @Modifying
    @Query(value = "DELETE FROM actors_cast WHERE actors_cast.actor_id = :id", nativeQuery = true)
    void deleteActorsCastByActorId(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM actors_cast WHERE film_id = :id", nativeQuery = true)
    void deleteActorsCastByMovieId(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM actors_cast WHERE film_id = :movie_id AND actor_id = :actor_id", nativeQuery = true)
    void deleteActorsCastByActorIdAndMovieId(@Param("movie_id") Long movieId, @Param("actor_id") Long actorId);
}
