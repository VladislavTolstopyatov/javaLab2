package mapper;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.MoviesDto.CreateMovieDto;
import com.example.javalab2.entity.Director;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.mapper.CreateMovieDtoMapper;
import com.example.javalab2.repository.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class CreateMovieDtoMapperTest {
    @MockBean
    private DirectorRepository directorRepository;

    @Autowired
    private CreateMovieDtoMapper createMovieDtoMapper;

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
            Genre.COMEDY,
            LocalDate.of(2003, 3, 3),
            150,
            director,
            Collections.emptyList(),
            Collections.emptyList());

    private static final CreateMovieDto createMovieDto = new CreateMovieDto("title",
            "description",
            Genre.COMEDY.toString(),
            director.getId(),
            LocalDate.of(2003, 3, 3),
            150);

    @Test
    void fromEntityToDto() {
        CreateMovieDto createMovieDto = createMovieDtoMapper.toDto(movie);
        assertTrue(createMovieDto.getTitle().equals(movie.getTitle()) &&
                createMovieDto.getDuration().equals(movie.getDuration()) &&
                createMovieDto.getDescription().equals(movie.getDescription()) &&
                createMovieDto.getGenre().equals(movie.getGenre().toString()) &&
                createMovieDto.getDateOfRelease().equals(movie.getDateOfRelease()) &&
                createMovieDto.getDirectorId().equals(movie.getDirector().getId()));
    }

    @Test
    void fromDtoToEntity() {
        when(directorRepository.findById(director.getId())).thenReturn(Optional.of(director));
        Movie movie = createMovieDtoMapper.toEntity(createMovieDto);
        assertTrue(createMovieDto.getTitle().equals(movie.getTitle()) &&
                createMovieDto.getDuration().equals(movie.getDuration()) &&
                createMovieDto.getDescription().equals(movie.getDescription()) &&
                createMovieDto.getGenre().equals(movie.getGenre().toString()) &&
                createMovieDto.getDateOfRelease().equals(movie.getDateOfRelease()) &&
                createMovieDto.getDirectorId().equals(movie.getDirector().getId()));
    }
}
