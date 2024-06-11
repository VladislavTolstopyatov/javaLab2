package com.example.javalab2.mapper;

import com.example.javalab2.dto.ActorCastDto;
import com.example.javalab2.entity.ActorsCast;
import com.example.javalab2.repository.ActorRepository;
import com.example.javalab2.repository.MovieRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ActorCastMapper implements Mappable<ActorsCast, ActorCastDto> {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;

    @Override
    public ActorCastDto toDto(ActorsCast actorsCastEntity) {
        if (actorsCastEntity == null) {
            return null;
        }
        return ActorCastDto.builder()
                .id(actorsCastEntity.getId())
                .actorFio(actorsCastEntity.getActor().getFio())
                .movieTitle(actorsCastEntity.getMovie().getTitle())
                .build();
    }

    @Override
    public ActorsCast toEntity(ActorCastDto dto) {
        if (dto == null) {
            return null;
        }
        return ActorsCast.builder()
                .id(dto.getId())
                .actor(actorRepository.findActorByFio(dto.getActorFio()))
                .movie(movieRepository.findMovieByTitle(dto.getMovieTitle()))
                .build();
    }

    @Override
    public List<ActorCastDto> toDto(List<ActorsCast> actorsCastsEntities) {
        if (actorsCastsEntities == null) {
            return null;
        }

        return actorsCastsEntities.stream().map(this::toDto).toList();
    }

    @Override
    public List<ActorsCast> toEntities(List<ActorCastDto> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(this::toEntity).toList();
    }
}
