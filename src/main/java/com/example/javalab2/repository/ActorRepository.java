package com.example.javalab2.repository;

import com.example.javalab2.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findActorByFio(String fio);

    List<Actor> findActorsByBirthdate(LocalDate birthdate);

    @Modifying
    @Query(value = "DELETE FROM actors WHERE actors.fio LIKE :fio", nativeQuery = true)
    void deleteActorByFio(@Param("fio") String fio);

    @Query(value = "SELECT DISTINCT a.* FROM actors a JOIN actors_cast ac ON a.actor_id = ac.actor_id JOIN movies m ON ac.film_id = m.movie_id WHERE m.movie_id = :id", nativeQuery = true)
    List<Actor> findActorsByMovieId(@Param("id") Long movieId);

}
