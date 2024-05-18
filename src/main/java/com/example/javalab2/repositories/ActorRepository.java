package com.example.javalab2.repositories;

import com.example.javalab2.entities.Actor;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findActorByFio(String fio);

    List<Actor> findActorsByBirthdate(LocalDate birthdate);

    @Modifying
    @Query("DELETE FROM actors WHERE actors.fio LIKE :fio")
    void deleteActorByFio(@Param("fio") String fio);
}
