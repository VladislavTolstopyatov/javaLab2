package com.example.javalab2.services;

import com.example.javalab2.dto.ActorCastDto;
import com.example.javalab2.entities.ActorsCast;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.mappers.ActorCastMapper;
import com.example.javalab2.repositories.ActorCastRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorCastService {
    private final ActorCastRepository actorCastRepository;
    private final ActorCastMapper actorCastMapper;

    public ActorCastDto saveActorCast(ActorCastDto actorCastDto) {
        return actorCastMapper.
                toDto(actorCastRepository.
                        save(actorCastMapper.toEntity(actorCastDto)));
    }

    public List<ActorCastDto> findAllActorsCasts() {
        return actorCastMapper.toDto(actorCastRepository.findAll());
    }

    public void deleteAllActorsCast() {
        actorCastRepository.deleteAll();
    }

    public List<ActorCastDto> findAllActorsCastByMovieTitle(String title) {
        return actorCastMapper.toDto(actorCastRepository.findActorsCastsByMovie_Title(title));
    }

    public List<ActorCastDto> findAllActorsCastByActorFio(String fio) {
        return actorCastMapper.toDto(actorCastRepository.findActorsCastsByActor_Fio(fio));
    }

    public void deleteActorCastById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        actorCastRepository.deleteById(id);
    }

    public ActorCastDto findActorCastById(Long id) throws ModelNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        Optional<ActorsCast> actorsCastOptional = actorCastRepository.findById(id);
        if (actorsCastOptional.isPresent()) {
            return actorCastMapper.toDto(actorsCastOptional.get());
        } else {
            throw new ModelNotFoundException(String.format("ActorCast with id %d", id));
        }
    }

    public List<ActorCastDto> findActorsCastsByMovieTitleAndActorFio(String fio, String title) {
        return actorCastMapper.toDto(actorCastRepository.
                findActorsCastsByActor_FioAndMovie_Title(fio, title));
    }
}
