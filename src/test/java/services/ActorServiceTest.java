package services;


import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entities.Actor;
import com.example.javalab2.exceptions.ActorFioAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.mappers.ActorMapper;
import com.example.javalab2.repositories.ActorRepository;
import com.example.javalab2.services.ActorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    void findActorByIdWhenActorExists() throws ModelNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findById(actorDto.getId())).thenReturn(Optional.of(actor));
        when(actorMapper.toDto(actor)).thenReturn(actorDto);
        assertThat(actorService.getActorById(actor.getId())).isEqualTo(actorDto);
    }

    @Test
    void findActorByIdWhenActorNotExists() throws ModelNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findById(actorDto.getId())).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> actorService.getActorById(actor.getId()));
    }

    @Test
    void findActorByFioWhenActorExists() throws ModelNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findActorByFio(actorDto.getFio())).thenReturn(actor);
        when(actorMapper.toDto(actor)).thenReturn(actorDto);
        ActorDto result = actorService.findActorByFio(actorDto.getFio());
        assertThat(result).isEqualTo(actorDto);
    }

    @Test
    void findActorByFioWhenActorNotExists() throws ModelNotFoundException {
        final ActorDto actorDto = getActorDto();
        final Actor actor = getActor();

        when(actorRepository.findActorByFio(actorDto.getFio())).thenReturn(null);
        assertThrows(ModelNotFoundException.class, () -> actorService.findActorByFio(actor.getFio()));
    }

    @Test
    void deleteActorByIdWithNotValidId() {
        final Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> actorService.deleteActorById(id));
    }

    @Test
    void findActorByIdWithNotValidId() {
        final Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> actorService.getActorById(id));
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
    void saveActorWhenFioUnique() throws ModelNotFoundException, ActorFioAlreadyExistsException {
        final Actor actor = getActor();
        when(actorRepository.save(any())).thenReturn(actor);
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        when(actorMapper.toDto(actor)).thenReturn(getActorDto());
        actorService.saveActor(getActorDto());
        ActorDto result = actorService.getActorById(actor.getId());
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
