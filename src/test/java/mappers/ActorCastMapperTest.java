package mappers;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.ActorCastDto;
import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entities.Actor;
import com.example.javalab2.entities.ActorsCast;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.enums.Genre;
import com.example.javalab2.mappers.ActorCastMapper;
import com.example.javalab2.repositories.ActorRepository;
import com.example.javalab2.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class ActorCastMapperTest {
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private ActorRepository actorRepository;
    @Autowired
    private ActorCastMapper actorCastMapper;

    private static final Movie movie = new Movie(1L,
            "title",
            "description",
            Genre.COMEDY,
            LocalDate.of(2003, 3, 3),
            150,
            null,
            Collections.emptyList());

    private static final Actor actor = new Actor(1L,
            "fio",
            LocalDate.of(2003, 3, 3));

    private static final ActorsCast actorsCast = new ActorsCast(1L, actor, movie);
    private static final ActorCastDto actorCastDto = new ActorCastDto(1L, actor.getFio(), movie.getTitle());

    @Test
    void fromEntityToDto() {
        ActorCastDto actorCastDto = actorCastMapper.toDto(actorsCast);
        assertTrue(actorCastDto.getId().equals(actorsCast.getId()) &&
                actorCastDto.getActorFio().equals(actorsCast.getActor().getFio()) &&
                actorCastDto.getMovieTitle().equals(actorsCast.getMovie().getTitle()));
    }

    @Test
    void fromDtoToEntity() {
        when(movieRepository.findMovieByTitle(actorCastDto.getMovieTitle())).thenReturn(movie);
        when(actorRepository.findActorByFio(actorCastDto.getActorFio())).thenReturn(actor);
        ActorsCast actorsCast = actorCastMapper.toEntity(actorCastDto);

        assertTrue(actorCastDto.getId().equals(actorsCast.getId()) &&
                actorCastDto.getActorFio().equals(actorsCast.getActor().getFio()) &&
                actorCastDto.getMovieTitle().equals(actorsCast.getMovie().getTitle()));
    }
}
