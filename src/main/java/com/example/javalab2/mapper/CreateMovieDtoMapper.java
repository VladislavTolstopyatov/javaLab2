package com.example.javalab2.mapper;

import com.example.javalab2.dto.MoviesDto.CreateMovieDto;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.repository.DirectorRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CreateMovieDtoMapper implements Mappable<Movie, CreateMovieDto> {
    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public CreateMovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }
        return CreateMovieDto.builder().
                description(movie.getDescription())
                .title(movie.getTitle())
                .dateOfRelease(movie.getDateOfRelease())
                .duration(movie.getDuration())
                .genre(movie.getGenre().toString())
                .directorId(movie.getDirector().getId())
                .build();
    }

    @Override
    public Movie toEntity(CreateMovieDto createMovieDto) {
        if (createMovieDto == null) {
            return null;
        }
        return Movie.builder()
                .title(createMovieDto.getTitle())
                .description(createMovieDto.getDescription())
                .genre(Genre.find(createMovieDto.getGenre()).get())
                .id(null)
                .actorsCasts(Collections.emptyList())
                .feedbacks(Collections.emptyList())
                .dateOfRelease(createMovieDto.getDateOfRelease())
                .director(directorRepository.findById(createMovieDto.getDirectorId()).get())
                .duration(createMovieDto.getDuration())
                .build();


    }

    @Override
    public List<CreateMovieDto> toDto(List<Movie> movies) {
        if (movies == null) {
            return null;
        }
        return movies.stream().map(this::toDto).toList();
    }

    @Override
    public List<Movie> toEntities(List<CreateMovieDto> createMovieDtos) {
        if (createMovieDtos == null) {
            return null;
        }
        return createMovieDtos.stream().map(this::toEntity).toList();
    }
}
