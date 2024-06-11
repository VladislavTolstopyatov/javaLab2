package mapper;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entity.Director;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.mapper.FeedBackMapper;
import com.example.javalab2.mapper.MovieMapper;
import com.example.javalab2.repository.ActorCastRepository;
import com.example.javalab2.repository.ActorRepository;
import com.example.javalab2.repository.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class MovieMapperTest {
    @Autowired
    private MovieMapper movieMapper;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private ActorCastRepository actorCastRepository;

    @Autowired
    private FeedBackMapper feedBackMapper;

    private static final Director director = new Director(1L,
            "name",
            "surname",
            "patronymic",
            LocalDate.of(2003, 3, 3),
            Boolean.TRUE,
            Collections.emptyList());

    private static final Movie movie = new Movie(1L,
            "title",
            "description",
            Genre.COMEDY, LocalDate.of(2003, 3, 3),
            150,
            director,
            Collections.emptyList(),
            Collections.emptyList());

    private static final MovieDto movieDto = new MovieDto(1L,
            "title",
            "description",
            "COMEDY",
            "name surname patronymic",
            LocalDate.of(2003, 3, 3),
            150,
            Collections.emptyList(),
            Collections.emptyList());

    @Test
    public void fromEntityToDtoTest() {
        final MovieDto movieDto = movieMapper.toDto(movie);
        List<String> directorFio = List.of(movieDto.getDirectorFio().split(" "));

        String name = directorFio.get(0);
        String surname = directorFio.get(1);
        String patronymic = directorFio.get(2);
        when(actorRepository.findActorsByMovieId(movieDto.getId())).thenReturn(Collections.emptyList());

        assertTrue(movieDto.getId().equals(movie.getId()) &&
                movieDto.getTitle().equals(movie.getTitle()) &&
                movieDto.getDescription().equals(movie.getDescription()) &&
                movieDto.getGenre().equals(movie.getGenre().toString()) &&
                movieDto.getDuration().equals(movie.getDuration()) && movieDto.getFeedbackDtoList().size() ==
                movie.getFeedbacks().size() && movieDto.getFeedbackDtoList().containsAll(feedBackMapper.toDto(movie.getFeedbacks())) &&
                movieDto.getDateOfRelease().equals(movie.getDateOfRelease()) &&
                name.equals(movie.getDirector().getName()) &&
                surname.equals(movie.getDirector().getSurname()) &&
                patronymic.equals(movie.getDirector().getPatronymic()));
    }

    @Test
    public void fromDtoToEntityTest() {
        List<String> directorFio = List.of(movieDto.getDirectorFio().split(" "));
        String name = directorFio.get(0);
        String surname = directorFio.get(1);
        String patronymic = directorFio.get(2);

        when(directorRepository.findDirectorByNameAndSurnameAndPatronymic(
                directorFio.get(0),
                directorFio.get(1),
                directorFio.get(2))).thenReturn(director);
        when(actorCastRepository.findActorsCastsByMovieId(movieDto.getId())).thenReturn(Collections.emptyList());

        final Movie movie = movieMapper.toEntity(movieDto);

        assertTrue(movieDto.getId().equals(movie.getId()) &&
                movieDto.getTitle().equals(movie.getTitle()) &&
                movieDto.getDescription().equals(movie.getDescription()) &&
                movieDto.getGenre().equals(movie.getGenre().toString()) &&
                movieDto.getDuration().equals(movie.getDuration()) && movieDto.getFeedbackDtoList().size() ==
                movie.getFeedbacks().size() && movieDto.getFeedbackDtoList().containsAll(feedBackMapper.toDto(movie.getFeedbacks())) &&
                movieDto.getDateOfRelease().equals(movie.getDateOfRelease()) &&
                name.equals(movie.getDirector().getName()) &&
                surname.equals(movie.getDirector().getSurname()) &&
                patronymic.equals(movie.getDirector().getPatronymic()));
    }
}
