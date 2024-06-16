package com.example.javalab2.controller;

import com.example.javalab2.dto.MoviesDto.CreateMovieDto;
import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.exception.MovieTitleAlreadyExistsException;
import com.example.javalab2.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Movie Controller",description = "Обрабатывает запросы, связанные с фильмами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Operation(summary = "Добавить новый фильм")
    @PostMapping
    public ResponseEntity<String> saveMovie(@RequestBody CreateMovieDto createMovieDto) throws MovieTitleAlreadyExistsException {
        movieService.saveMovie(createMovieDto);
        return new ResponseEntity<>("Movie saved successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Получить фильм по id")
    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable("id") Long movieId) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findMovieById(movieId), HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех фильмов")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return new ResponseEntity<>(movieService.findAllMovies(), HttpStatus.OK);
    }

    @Operation(summary = "Получить фильм по описанию")
    @GetMapping("/movie/description")
    public ResponseEntity<MovieDto> getMovieByDescription(@RequestParam String description) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findMovieByDescription(description), HttpStatus.OK);
    }

    @Operation(summary = "Получить фильм по названию")
    @GetMapping("/movie/title")
    public ResponseEntity<MovieDto> getMovieByTitle(@RequestParam String title) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findMovieByTitle(title), HttpStatus.OK);
    }

    @Operation(summary = "Получить фильмы по дате релиза")
    @GetMapping("/dateOfRelease")
    public ResponseEntity<List<MovieDto>> getMoviesByDateOfRelease(@RequestParam LocalDate dateOfRelease) {
        return new ResponseEntity<>(movieService.findMoviesByDateOfRelease(dateOfRelease), HttpStatus.OK);
    }

    @Operation(summary = "Получить фильмы по жанру")
    @GetMapping("/genre")
    public ResponseEntity<List<MovieDto>> getMoviesByGenre(@RequestParam Genre genre) {
        return new ResponseEntity<>(movieService.findMoviesByGenre(genre), HttpStatus.OK);
    }

    @Operation(summary = "Получить фильмы по длительности")
    @GetMapping("/duration")
    public ResponseEntity<List<MovieDto>> getMoviesByDuration(@RequestParam Integer duration) {
        return new ResponseEntity<>(movieService.findMoviesByDuration(duration), HttpStatus.OK);
    }

    @Operation(summary = "Получить фильмы по id режиссёра")
    @GetMapping("/directorsId/{id}")
    public ResponseEntity<List<MovieDto>> getMoviesByDirectorId(@PathVariable("id") Long directorId) {
        return new ResponseEntity<>(movieService.findMoviesByDirectorId(directorId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить все фильмы")
    @DeleteMapping
    public ResponseEntity<String> deleteAllMovies() {
        movieService.deleteAllMovies();
        return new ResponseEntity<>("All movies have been deleted", HttpStatus.OK);
    }

    @Operation(summary = "Удалить фильм по id")
    @DeleteMapping("/movie/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable("id") Long movieId) {
        movieService.deleteMovieById(movieId);
        return new ResponseEntity<>(String.format("Movie with id %d have been deleted", movieId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить фильм по названию")
    @DeleteMapping("/movie/title")
    public ResponseEntity<String> deleteMovieByTitle(@RequestParam String title) {
        movieService.deleteMovieByTitle(title);
        return new ResponseEntity<>(String.format("Movie with title %s have been deleted", title), HttpStatus.OK);
    }

    @Operation(summary = "Удалить фильмы по id режиссёра")
    @DeleteMapping("/directorId/{id}")
    public ResponseEntity<String> deleteMoviesByDirectorId(@PathVariable("id") Long directorId) {
        movieService.deleteMoviesByDirectorId(directorId);
        return new ResponseEntity<>(String.format("Movies have been deleted by directorId %d", directorId), HttpStatus.OK);
    }
}
