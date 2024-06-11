package com.example.javalab2.repository;

import com.example.javalab2.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    List<Director> findDirectorsByName(String name);

    List<Director> findDirectorsBySurname(String name);

    List<Director> findDirectorsByPatronymic(String name);

    List<Director> findDirectorsByBirthdate(LocalDate birthDate);

    List<Director> findDirectorsByOscar(Boolean oscar);

//    @Query("SELECT * FROM directors d JOIN movies m on d.director_id = m.director_id WHERE movie_id = :id")
//    Director findDirectorByMovieId(@Param("id") Long movieId);

    Director findDirectorByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

    @Modifying
    @Query(value = "DELETE FROM directors WHERE directors.name LIKE :name " +
            "AND surname LIKE :surname " +
            "AND patronymic LIKE :patronymic",nativeQuery = true)
    void deleteDirectorByNameAndSurnameAndPatronymic(@Param("name") String name, @Param("surname") String surname, @Param("patronymic") String patronymic);
}
