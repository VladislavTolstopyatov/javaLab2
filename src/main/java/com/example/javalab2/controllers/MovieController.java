package com.example.javalab2.controllers;

import com.example.javalab2.dto.MoviesDto.CreateMovieDto;
import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entities.enums.Genre;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.MovieTitleAlreadyExistsException;
import com.example.javalab2.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/movies/save")
    public ResponseEntity<String> saveMovie(@RequestBody CreateMovieDto createMovieDto) throws MovieTitleAlreadyExistsException {
        movieService.saveMovie(createMovieDto);
        return new ResponseEntity<>("Movie saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable("id") Long movieId) throws ModelNotFoundException {
        return new ResponseEntity<>(movieService.findMovieById(movieId), HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return new ResponseEntity<>(movieService.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/movies/descriptions/{description}")
    public ResponseEntity<MovieDto> getMovieByDescription(@PathVariable("description") String description) throws ModelNotFoundException {
        return new ResponseEntity<>(movieService.findMovieByDescription(description), HttpStatus.OK);
    }

    @GetMapping("/movies/dateOfReleases/{dateOfRelease}")
    public ResponseEntity<List<MovieDto>> getMoviesByDateOfRelease(@PathVariable("dateOfRelease") LocalDate dateOfRelease) {
        return new ResponseEntity<>(movieService.findMoviesByDateOfRelease(dateOfRelease), HttpStatus.OK);
    }

    @GetMapping("/movies/genres/{genre}")
    public ResponseEntity<List<MovieDto>> getMoviesByGenre(@PathVariable("genre") Genre genre) {
        return new ResponseEntity<>(movieService.findMoviesByGenre(genre), HttpStatus.OK);
    }

    @GetMapping("/movies/durations/{duration}")
    public ResponseEntity<List<MovieDto>> getMoviesByDuration(@PathVariable("duration") Integer duration) {
        return new ResponseEntity<>(movieService.findMoviesByDuration(duration), HttpStatus.OK);
    }

    @GetMapping("/movies/directorsId/{id}")
    public ResponseEntity<List<MovieDto>> getMoviesByDirectorId(@PathVariable("id") Long directorId) {
        return new ResponseEntity<>(movieService.findMoviesByDirectorId(directorId), HttpStatus.OK);
    }

    @DeleteMapping("/movies/delete")
    public ResponseEntity<String> deleteAllMovies() {
        movieService.deleteAllMovies();
        return new ResponseEntity<>("All movies have been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/movies/delete/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable("id") Long movieId) {
        movieService.deleteMovieById(movieId);
        return new ResponseEntity<>(String.format("Movie with id %d have been deleted", movieId), HttpStatus.OK);
    }

    @DeleteMapping("/movies/deleteByTitle/{title}")
    public ResponseEntity<String> deleteMovieByTitle(@PathVariable("title") String title) {
        movieService.deleteMovieByTitle(title);
        return new ResponseEntity<>(String.format("Movie with title %s have been deleted", title), HttpStatus.OK);
    }

    @DeleteMapping("/movies/deleteByDirectorId/{id}")
    public ResponseEntity<String> deleteMoviesByDirectorId(@PathVariable("id") Long directorId) {
        movieService.deleteMoviesByDirectorId(directorId);
        return new ResponseEntity<>(String.format("Movies have been deleted by directorId %d", directorId), HttpStatus.OK);
    }
}
