package com.example.javalab2.services;

import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entities.Actor;
import com.example.javalab2.exceptions.ActorFioAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.mappers.ActorMapper;
import com.example.javalab2.repositories.ActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"main"})
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public ActorDto findActorById(Long id) throws ModelNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        Optional<Actor> optionalActor = actorRepository.findById(id);
        if (optionalActor.isPresent()) {
            Actor actor = optionalActor.get();
            return actorMapper.toDto(actor);
        } else {
            throw new ModelNotFoundException(String.format("Actor with id %d not found", id));
        }
    }

    @Cacheable
    public List<ActorDto> findAllActors() {
        return actorMapper.toDto(actorRepository.findAll());
    }

    public void deleteAllActors() {
        actorRepository.deleteAll();
    }

    public void deleteActorById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        actorRepository.deleteById(id);
    }

    @Cacheable
    public ActorDto findActorByFio(String fio) throws ModelNotFoundException {
        Actor actor = actorRepository.findActorByFio(fio);
        if (actor != null) {
            return actorMapper.toDto(actor);
        } else {
            throw new ModelNotFoundException(String.format("Actor with fio %s not found", fio));
        }
    }

    public List<ActorDto> findActorsByBirthdate(LocalDate birthdate) {
        List<Actor> actors = actorRepository.findActorsByBirthdate(birthdate);
        return actorMapper.toDto(actors);
    }

    public void deleteActorByFio(String fio) {
        actorRepository.deleteActorByFio(fio);
    }

    public ActorDto saveActor(ActorDto actorDto) throws ActorFioAlreadyExistsException {
        if (actorRepository.findActorByFio(actorDto.getFio()) != null) {
            throw new ActorFioAlreadyExistsException(String.format("Actor with fio %s already exists",
                    actorDto.getFio()));
        }
        Actor actor = actorMapper.toEntity(actorDto);
        return actorMapper.toDto(actorRepository.save(actor));
    }
}
