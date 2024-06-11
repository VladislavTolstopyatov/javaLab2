package com.example.javalab2.mapper;

import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.repository.ActorCastRepository;
import com.example.javalab2.repository.ActorRepository;
import com.example.javalab2.repository.DirectorRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MovieMapper implements Mappable<Movie, MovieDto> {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorCastRepository actorCastRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private ActorMapper actorMapper;

    @Override
    public MovieDto toDto(Movie movieEntity) {
        if (movieEntity == null) {
            return null;
        }

        return MovieDto.builder()
                .id(movieEntity.getId())
                .title(movieEntity.getTitle())
                .description(movieEntity.getDescription())
                .genre(movieEntity.getGenre().toString())
                .dateOfRelease(movieEntity.getDateOfRelease())
                .directorFio(movieEntity.getDirector().getName() +
                        " " + movieEntity.getDirector().getSurname() +
                        " " + movieEntity.getDirector().getPatronymic())
                .feedbackDtoList(feedBackMapper.toDto(movieEntity.getFeedbacks()))
                .duration(movieEntity.getDuration())
                .actorDtoList(actorMapper.toDto(actorRepository.findActorsByMovieId(movieEntity.getId())))
                .build();
    }

    @Override
    public Movie toEntity(MovieDto dto) {
        if (dto == null) {
            return null;
        }

        List<String> fio = List.of(dto.getDirectorFio().split(" "));

        return Movie.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .genre(Genre.find(dto.getGenre()).get())
                .director(directorRepository.findDirectorByNameAndSurnameAndPatronymic(
                        fio.get(0),
                        fio.get(1),
                        fio.get(2)))
                .feedbacks(feedBackMapper.toEntities(dto.getFeedbackDtoList()))
                .dateOfRelease(dto.getDateOfRelease())
                .actorsCasts(actorCastRepository.findActorsCastsByMovieId(dto.getId()))
                .build();
    }

    @Override
    public List<MovieDto> toDto(List<Movie> movieEntities) {
        if (movieEntities == null) {
            return null;
        }

        return movieEntities.stream().map(this::toDto).toList();
    }

    @Override
    public List<Movie> toEntities(List<MovieDto> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(this::toEntity).toList();
    }
}
