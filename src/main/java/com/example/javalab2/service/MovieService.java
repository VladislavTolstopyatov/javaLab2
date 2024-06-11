package com.example.javalab2.service;

import com.example.javalab2.dto.MoviesDto.CreateMovieDto;
import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.exception.MovieTitleAlreadyExistsException;
import com.example.javalab2.mapper.CreateMovieDtoMapper;
import com.example.javalab2.mapper.MovieMapper;
import com.example.javalab2.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"main"})
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CreateMovieDtoMapper createMovieDtoMapper;

    public CreateMovieDto saveMovie(CreateMovieDto createMovieDto) throws MovieTitleAlreadyExistsException {
        Movie movie = movieRepository.findMovieByTitle(createMovieDto.getTitle());
        if (movie != null) {
            throw new MovieTitleAlreadyExistsException(String.format("Movie with title %s already exists",
                    createMovieDto.getTitle()));
        } else {
            return createMovieDtoMapper.toDto(movieRepository.save(createMovieDtoMapper.toEntity(createMovieDto)));
        }
    }

    public MovieDto findMovieById(Long id) throws EntityNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return movieMapper.toDto(optionalMovie.get());
        } else {
            throw new EntityNotFoundException(String.format("Movie with id %d not found", id));
        }
    }

    public void deleteMovieById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        movieRepository.deleteById(id);
    }

    @Cacheable
    public List<MovieDto> findAllMovies() {
        return movieMapper.toDto(movieRepository.findAll());
    }

    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }

    @Cacheable
    public MovieDto findMovieByTitle(String title) throws EntityNotFoundException {
        Movie movie = movieRepository.findMovieByTitle(title);
        if (movie != null) {
            return movieMapper.toDto(movie);
        } else {
            throw new EntityNotFoundException(String.format("Movie with title %s not found", title));
        }
    }

    public MovieDto findMovieByDescription(String description) throws EntityNotFoundException {
        Movie movie = movieRepository.findMovieByDescription(description);
        if (movie != null) {
            return movieMapper.toDto(movie);
        } else {
            throw new EntityNotFoundException(String.format("Movie with description %s not found", description));
        }
    }

    @Cacheable
    public List<MovieDto> findMoviesByDateOfRelease(LocalDate dateOfRelease) {
        return movieMapper.toDto(movieRepository.findMoviesByDateOfRelease(dateOfRelease));
    }

    @Cacheable
    public List<MovieDto> findMoviesByGenre(Genre genre) {
        return movieMapper.toDto(movieRepository.findMoviesByGenre(genre));
    }

    @Cacheable
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
