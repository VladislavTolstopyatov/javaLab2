package service;


import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entity.Actor;
import com.example.javalab2.exception.ActorFioAlreadyExistsException;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.mapper.ActorMapper;
import com.example.javalab2.repository.ActorRepository;
import com.example.javalab2.service.ActorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorMapper actorMapper;

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    @Test
    void deleteActorById() {
        final Long id = getActor().getId();
        actorService.deleteActorById(id);
        verify(actorRepository, times(1)).deleteById(id);
    }

    @Test
    void findActorByIdWhenActorExists() throws EntityNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findById(actorDto.getId())).thenReturn(Optional.of(actor));
        when(actorMapper.toDto(actor)).thenReturn(actorDto);
        assertThat(actorService.findActorById(actor.getId())).isEqualTo(actorDto);
    }

    @Test
    void findActorByIdWhenActorNotExists() throws EntityNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findById(actorDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> actorService.findActorById(actor.getId()));
    }

    @Test
    void findActorByFioWhenActorExists() throws EntityNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findActorByFio(actorDto.getFio())).thenReturn(actor);
        when(actorMapper.toDto(actor)).thenReturn(actorDto);
        ActorDto result = actorService.findActorByFio(actorDto.getFio());
        assertThat(result).isEqualTo(actorDto);
    }

    @Test
    void findActorByFioWhenActorNotExists() throws EntityNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findActorByFio(actorDto.getFio())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> actorService.findActorByFio(actor.getFio()));
    }

    @Test
    void deleteActorByIdWithNotValidId() {
        final Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> actorService.deleteActorById(id));
    }

    @Test
    void findActorByIdWithNotValidId() {
        final Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> actorService.findActorById(id));
    }

    @Test
    void findActorsByBirthdateWhenActorsExists() {
        final List<Actor> actorList = List.of(getActor());
        final List<ActorDto> actorDtos = List.of(getActorDto());

        when(actorRepository.findActorsByBirthdate(actorList.get(0).getBirthdate()))
                .thenReturn(actorList);
        when(actorMapper.toDto(actorList))
                .thenReturn(actorDtos);


        assertThat(actorService.findActorsByBirthdate(actorList.get(0).getBirthdate()))
                .hasSize(1);
        actorService.findActorsByBirthdate(actorList.get(0).getBirthdate()).stream().map(ActorDto::getId)
                .forEach(id -> assertThat(id).isEqualTo(1));
    }

    @Test
    void findActorsByBirthdateWhenActorsNotExists() {
        when(actorRepository.findActorsByBirthdate(getActor().getBirthdate()))
                .thenReturn(null);
        assertThat(actorService.findActorsByBirthdate(getActor().getBirthdate())).isEmpty();
    }

    @Test
    void findAllActorsWhenActorsExists() {
        final List<Actor> actorList = List.of(getActor());
        List<ActorDto> actorDtos = List.of(getActorDto());

        when(actorRepository.findAll())
                .thenReturn(actorList);
        when(actorMapper.toDto(actorList))
                .thenReturn(actorDtos);


        assertThat(actorService.findAllActors())
                .hasSize(1);
        actorService.findAllActors().stream().map(ActorDto::getId)
                .forEach(id -> assertThat(id).isEqualTo(1));
    }

    @Test
    void findAllActorsWhenActorsNotExists() {
        when(actorRepository.findAll())
                .thenReturn(null);
        assertThat(actorService.findAllActors()).isEmpty();
    }

    @Test
    void saveActorWhenFioUnique() throws EntityNotFoundException, ActorFioAlreadyExistsException {
        final Actor actor = getActor();
        when(actorRepository.save(any())).thenReturn(actor);
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        when(actorMapper.toDto(actor)).thenReturn(getActorDto());
        actorService.saveActor(getActorDto());
        ActorDto result = actorService.findActorById(actor.getId());
        assertThat(result).isEqualTo(getActorDto());
    }

    @Test
    void saveActorWhenFioNotUnique() {
        final ActorDto actorDto = getActorDto();
        when(actorRepository.findActorByFio(actorDto.getFio())).thenReturn(getActor());
        assertThrows(ActorFioAlreadyExistsException.class, () -> actorService.saveActor(actorDto));
    }

    @Test
    void deleteActorByFio() {
        final String fio = getActor().getFio();
        actorService.deleteActorByFio(fio);
        verify(actorRepository, times(1)).deleteActorByFio(fio);
    }

    @Test
    void deleteAllActors() {
        actorService.deleteAllActors();
        verify(actorRepository, times(1)).deleteAll();
    }

    private static ActorDto getActorDto() {
        return new ActorDto(1L,
                "fio",
                LocalDate.of(2003, 3, 3));
    }


    private static Actor getActor() {
        return new Actor(1L,
                "fio",
                LocalDate.of(2003, 3, 3));
    }
}
