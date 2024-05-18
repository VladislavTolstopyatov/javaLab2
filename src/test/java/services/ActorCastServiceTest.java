package services;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.ActorCastDto;
import com.example.javalab2.entities.Actor;
import com.example.javalab2.entities.ActorsCast;
import com.example.javalab2.entities.Director;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.enums.Genre;
import com.example.javalab2.mappers.ActorCastMapper;
import com.example.javalab2.repositories.ActorCastRepository;
import com.example.javalab2.repositories.ActorRepository;
import com.example.javalab2.repositories.MovieRepository;
import com.example.javalab2.services.ActorCastService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class ActorCastServiceTest {
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private ActorRepository actorRepository;
    @MockBean
    private ActorCastRepository actorCastRepository;
    @MockBean
    private ActorCastMapper actorCastMapper;
    @Autowired
    private ActorCastService actorCastService;

    @Test
    public void saveActorCast() {
        final ActorsCast actorsCast = getActorCast();
        final ActorCastDto actorCastDto = getActorCastDto();

        when(movieRepository.findMovieByTitle(actorCastDto.getMovieTitle())).thenReturn(getMovie());
        when(actorRepository.findActorByFio(actorCastDto.getActorFio())).thenReturn(actor);

        when(actorCastMapper.toEntity(actorCastDto)).thenReturn(actorsCast);
        when(actorCastRepository.save(actorsCast)).thenReturn(actorsCast);
        when(actorCastMapper.toDto(actorsCast)).thenReturn(actorCastDto);
        assertThat(actorCastDto).isEqualTo(actorCastService.saveActorCast(actorCastDto));
    }

    @Test
    void findAllActorsCastWhenActorsCastsExists() {
        final List<ActorCastDto> actorCastDtoList = List.of(getActorCastDto());
        final List<ActorsCast> actorsCasts = List.of(getActorCast());
        when(actorCastRepository.findAll()).thenReturn(actorsCasts);
        when(actorCastMapper.toDto(actorsCasts)).thenReturn(actorCastDtoList);
        assertThat(actorCastDtoList).isEqualTo(actorCastService.findAllActorsCasts());
    }

    @Test
    void findAllActorsCastsWhenActorsCastsNotExists() {
        when(actorCastRepository.findAll()).thenReturn(null);
        assertThat(actorCastService.findAllActorsCasts()).isEmpty();
    }

    @Test
    void deleteAllActorsCasts() {
        actorCastService.deleteAllActorsCast();
        verify(actorCastRepository, times(1)).deleteAll();
    }

    @Test
    void findActorsCastsByMovieTitleWhenActorsCastsExists() {
        final List<ActorCastDto> actorCastDtoList = List.of(getActorCastDto());
        final List<ActorsCast> actorsCasts = List.of(getActorCast());
        final String title = getMovie().getTitle();

        when(actorCastRepository.findActorsCastsByMovie_Title(title)).thenReturn(actorsCasts);
        when(actorCastMapper.toDto(actorsCasts)).thenReturn(actorCastDtoList);
        assertThat(actorCastDtoList).isEqualTo(actorCastService.findAllActorsCastByMovieTitle(title));
    }

    @Test
    void findActorsCastsByMovieTitleWhenActorsCastsNotExists() {
        final String title = getMovie().getTitle();
        when(actorCastRepository.findActorsCastsByMovie_Title(title)).thenReturn(null);
        assertThat(actorCastService.findAllActorsCasts()).isEmpty();
    }


    @Test
    void findActorsCastsByActorFioWhenActorsCastsExists() {
        final List<ActorCastDto> actorCastDtoList = List.of(getActorCastDto());
        final List<ActorsCast> actorsCasts = List.of(getActorCast());
        final String fio = actor.getFio();

        when(actorCastRepository.findActorsCastsByActor_Fio(fio)).thenReturn(actorsCasts);
        when(actorCastMapper.toDto(actorsCasts)).thenReturn(actorCastDtoList);
        assertThat(actorCastDtoList).isEqualTo(actorCastService.findAllActorsCastByActorFio(fio));
    }

    @Test
    void findActorsCastsByActorFioWhenActorsCastsNotExists() {
        final String fio = actor.getFio();
        when(actorCastRepository.findActorsCastsByActor_Fio(fio)).thenReturn(null);
        assertThat(actorCastService.findAllActorsCastByActorFio(fio)).isEmpty();
    }


    private static final Actor actor = new Actor(1L,
            "fio",
            LocalDate.of(2003, 3, 3));

    private static final Director director = new Director(1L,
            "name",
            "surname",
            "patronymic",
            LocalDate.of(2003, 3, 3),
            Boolean.TRUE,
            Collections.emptyList());

    private static Movie getMovie() {
        return new Movie(1L,
                "title",
                "description",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList());
    }

    private static ActorsCast getActorCast() {
        return new ActorsCast(1L,
                actor,
                getMovie());
    }

    private static ActorCastDto getActorCastDto() {
        return new ActorCastDto(1L,
                "fio",
                "title");
    }
}
