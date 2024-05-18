package com.example.javalab2.services;

import com.example.javalab2.dto.MovieDto;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.enums.Genre;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.MovieTitleAlreadyExistsException;
import com.example.javalab2.mappers.MovieMapper;
import com.example.javalab2.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieDto saveMovie(MovieDto movieDto) throws MovieTitleAlreadyExistsException {
        Movie movie = movieRepository.findMovieByTitle(movieDto.getTitle());
        if (movie != null) {
            throw new MovieTitleAlreadyExistsException(String.format("Movie with title %s already exists",
                    movieDto.getTitle()));
        } else {
            return movieMapper.toDto(movieRepository.save(movieMapper.toEntity(movieDto)));
        }
    }

    public MovieDto findMovieById(Long id) throws ModelNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return movieMapper.toDto(optionalMovie.get());
        } else {
            throw new ModelNotFoundException(String.format("Movie with id %d not found", id));
        }
    }

    public void deleteMovieById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        movieRepository.deleteById(id);
    }

    public List<MovieDto> findAllMovies() {
        return movieMapper.toDto(movieRepository.findAll());
    }

    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }

    public MovieDto findMovieByTitle(String title) throws ModelNotFoundException {
        Movie movie = movieRepository.findMovieByTitle(title);
        if (movie != null) {
            return movieMapper.toDto(movie);
        } else {
            throw new ModelNotFoundException(String.format("Movie with title %s not found", title));
        }
    }

    public MovieDto findMovieByDescription(String description) throws ModelNotFoundException {
        Movie movie = movieRepository.findMovieByDescription(description);
        if (movie != null) {
            return movieMapper.toDto(movie);
        } else {
            throw new ModelNotFoundException(String.format("Movie with description %s not found", description));
        }
    }

    public List<MovieDto> findMoviesByDateOfRelease(LocalDate dateOfRelease) {
        return movieMapper.toDto(movieRepository.findMoviesByDateOfRelease(dateOfRelease));
    }

    public List<MovieDto> findMoviesByGenre(Genre genre) {
        return movieMapper.toDto(movieRepository.findMoviesByGenre(genre));
    }

    public List<MovieDto> findMoviesByDuration(Integer duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("duration <= 0");
        }
        return movieMapper.toDto(movieRepository.findMoviesByDuration(duration));
    }

    public List<MovieDto> findMoviesByDirectorId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        return movieMapper.toDto(movieRepository.findMoviesByDirectorId(id));
    }

    public void deleteMovieByTitle(String title) {
        movieRepository.deleteByTitle(title);
    }

    public void deleteMoviesByDirectorId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        movieRepository.deleteMoviesByDirectorId(id);
    }
}
