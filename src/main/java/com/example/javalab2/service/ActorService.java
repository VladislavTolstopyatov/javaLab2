package com.example.javalab2.service;

import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entity.Actor;
import com.example.javalab2.exception.ActorFioAlreadyExistsException;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.mapper.ActorMapper;
import com.example.javalab2.repository.ActorRepository;
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
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public ActorDto findActorById(Long id) throws EntityNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        Optional<Actor> optionalActor = actorRepository.findById(id);
        if (optionalActor.isPresent()) {
            Actor actor = optionalActor.get();
            return actorMapper.toDto(actor);
        } else {
            throw new EntityNotFoundException(String.format("Actor with id %d not found", id));
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
    public ActorDto findActorByFio(String fio) throws EntityNotFoundException {
        Actor actor = actorRepository.findActorByFio(fio);
        if (actor != null) {
            return actorMapper.toDto(actor);
        } else {
            throw new EntityNotFoundException(String.format("Actor with fio %s not found", fio));
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
