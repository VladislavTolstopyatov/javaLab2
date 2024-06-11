package service;

import com.example.javalab2.dto.MoviesDto.CreateMovieDto;
import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entity.Director;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.exception.MovieTitleAlreadyExistsException;
import com.example.javalab2.mapper.CreateMovieDtoMapper;
import com.example.javalab2.mapper.MovieMapper;
import com.example.javalab2.repository.MovieRepository;
import com.example.javalab2.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;

    @Mock
    private CreateMovieDtoMapper createMovieDtoMapper;
    @InjectMocks
    private MovieService movieService;

    private static final Director director = new Director(1L,
            "name",
            "surname",
            "patronymic",
            LocalDate.of(2003, 3, 3),
            Boolean.TRUE,
            Collections.emptyList());

    @Test
    public void saveMovieWhenTitleUnique() throws MovieTitleAlreadyExistsException {
        final Movie movie = getMovie();
        final CreateMovieDto createMovieDto = getCreateMovieDto();

        when(movieRepository.findMovieByTitle(createMovieDto.getTitle())).thenReturn(null);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(createMovieDtoMapper.toEntity(createMovieDto)).thenReturn(movie);
        when(createMovieDtoMapper.toDto(movie)).thenReturn(createMovieDto);
        assertThat(createMovieDto).isEqualTo(movieService.saveMovie(createMovieDto));
    }

    @Test
    public void saveMovieWhenTitleNotUnique() {
        final Movie movie = getMovie();
        final CreateMovieDto createMovieDto = getCreateMovieDto();

        when(movieRepository.findMovieByTitle(createMovieDto.getTitle())).thenReturn(movie);
        assertThrows(MovieTitleAlreadyExistsException.class, () -> movieService.saveMovie(createMovieDto));
    }

    @Test
    public void deleteMovieByInvalidId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> movieService.findMovieById(invalidId));
    }

    @Test
    public void deleteMovieByValidId() {
        final Long validId = 1L;
        movieService.deleteMovieById(validId);
        verify(movieRepository, times(1)).deleteById(validId);
    }

    @Test
    public void findMovieByInvalidId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> movieService.findMovieById(invalidId));
    }

    @Test
    public void findMovieByValidIdWhenMovieExists() throws EntityNotFoundException {
        final Movie movie = getMovie();
        final MovieDto movieDto = getMovieDto();

        when(movieRepository.findById(movieDto.getId())).thenReturn(Optional.of(movie));
        when(movieMapper.toDto(movie)).thenReturn(movieDto);
        assertThat(movieDto).isEqualTo(movieService.findMovieById(movieDto.getId()));
    }

    @Test
    public void findMovieByValidIdWhenMovieNotExists() {
        final MovieDto movieDto = getMovieDto();

        when(movieRepository.findById(movieDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> movieService.findMovieById(movieDto.getId()));
    }

    @Test
    public void findAllMoviesWhenMoviesExists() {
        final List<Movie> movieList = List.of(getMovie());
        final List<MovieDto> movieDtos = List.of(getMovieDto());

        when(movieRepository.findAll()).thenReturn(movieList);
        when(movieMapper.toDto(movieList)).thenReturn(movieDtos);
        assertThat(movieDtos.get(0)).isEqualTo(movieService.findAllMovies().get(0));
    }

    @Test
    public void findAllMoviesWhenMoviesNotExists() {
        when(movieRepository.findAll()).thenReturn(null);
        assertThat(movieService.findAllMovies()).isEmpty();
    }

    @Test
    public void deleteAllActors() {
        movieService.deleteAllMovies();
        verify(movieRepository, times(1)).deleteAll();
    }

    @Test
    public void findMovieByTitleWhenMovieExists() throws EntityNotFoundException {
        final String title = getMovie().getTitle();
        final MovieDto movieDto = getMovieDto();
        final Movie movie = getMovie();

        when(movieRepository.findMovieByTitle(title)).thenReturn(movie);
        when(movieMapper.toDto(movie)).thenReturn(movieDto);
        assertThat(movieDto).isEqualTo(movieService.findMovieByTitle(title));
    }

    @Test
    public void findMovieByTitleWhenMovieNotExists() {
        final String title = getMovieDto().getTitle();

        when(movieRepository.findMovieByTitle(title)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> movieService.findMovieByTitle(title));
    }

    @Test
    public void findMovieByDescriptionWhenMovieExists() throws EntityNotFoundException {
        final String description = getMovie().getDescription();
        final MovieDto movieDto = getMovieDto();
        final Movie movie = getMovie();

        when(movieRepository.findMovieByDescription(description)).thenReturn(movie);
        when(movieMapper.toDto(movie)).thenReturn(movieDto);
        assertThat(movieDto).isEqualTo(movieService.findMovieByDescription(description));
    }

    @Test
    public void findMovieByDescriptionWhenMovieNotExists() {
        final String description = getMovie().getDescription();

        when(movieRepository.findMovieByDescription(description)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> movieService.findMovieByDescription(description));
    }

    @Test
    public void findAllMoviesByDateOfReleaseWhenMoviesExists() {
        final List<Movie> movieList = List.of(getMovie());
        final List<MovieDto> movieDtos = List.of(getMovieDto());
        final LocalDate dateOfRelease = getMovieDto().getDateOfRelease();

        when(movieRepository.findMoviesByDateOfRelease(dateOfRelease)).thenReturn(movieList);
        when(movieMapper.toDto(movieList)).thenReturn(movieDtos);
        assertThat(movieDtos.get(0)).isEqualTo(movieService.findMoviesByDateOfRelease(dateOfRelease).get(0));
    }

    @Test
    public void findAllMoviesByDateOfReleaseWhenMoviesNotExists() {
        final LocalDate dateOfRelease = getMovieDto().getDateOfRelease();
        when(movieRepository.findMoviesByDateOfRelease(dateOfRelease)).thenReturn(null);
        assertThat(movieService.findMoviesByDateOfRelease(dateOfRelease)).isEmpty();
    }

    @Test
    public void findAllMoviesByGenreWhenMoviesExists() {
        final List<Movie> movieList = List.of(getMovie());
        final List<MovieDto> movieDtos = List.of(getMovieDto());
        final Genre genre = getMovie().getGenre();

        when(movieRepository.findMoviesByGenre(genre)).thenReturn(movieList);
        when(movieMapper.toDto(movieList)).thenReturn(movieDtos);
        assertThat(movieDtos.get(0)).isEqualTo(movieService.findMoviesByGenre(genre).get(0));
    }

    @Test
    public void findAllMoviesByGenreWhenMoviesNotExists() {
        final Genre genre = getMovie().getGenre();
        when(movieRepository.findMoviesByGenre(genre)).thenReturn(null);
        assertThat(movieService.findMoviesByGenre(genre)).isEmpty();
    }

    @Test
    public void findAllMoviesByDurationWhenMoviesExists() {
        final List<Movie> movieList = List.of(getMovie());
        final List<MovieDto> movieDtos = List.of(getMovieDto());
        final Integer duration = getMovieDto().getDuration();

        when(movieRepository.findMoviesByDuration(duration)).thenReturn(movieList);
        when(movieMapper.toDto(movieList)).thenReturn(movieDtos);
        assertThat(movieDtos.get(0)).isEqualTo(movieService.findMoviesByDuration(duration).get(0));
    }

    @Test
    public void findAllMoviesByDurationWhenMoviesNotExists() {
        final Integer duration = getMovieDto().getDuration();
        when(movieRepository.findMoviesByDuration(duration)).thenReturn(null);
        assertThat(movieService.findMoviesByDuration(duration)).isEmpty();
    }

    @Test
    public void findAllMoviesByInvalidDuration() {
        final Integer invalidDuration = -1;
        assertThrows(IllegalArgumentException.class, () -> movieService.findMoviesByDuration(invalidDuration));
    }

    @Test
    void deleteMovieByTitle() {
        final String title = getMovieDto().getTitle();
        movieService.deleteMovieByTitle(title);
        verify(movieRepository, times(1)).deleteByTitle(title);
    }

    @Test
    void deleteMoviesByDirectorId() {
        final Long directorId = 1L;
        movieService.deleteMoviesByDirectorId(directorId);
        verify(movieRepository, times(1)).deleteMoviesByDirectorId(directorId);
    }

    private static MovieDto getMovieDto() {
        return new MovieDto(1L,
                "title",
                "description",
                "COMEDY",
                "name surname patronymic",
                LocalDate.of(2003, 3, 3),
                150,
                Collections.emptyList(),
                Collections.emptyList());
    }


    private static Movie getMovie() {
        return new Movie(1L,
                "title",
                "description",
                Genre.COMEDY,
                LocalDate.of(2003, 3, 3),
                150,
                director,
                Collections.emptyList(),
                Collections.emptyList());
    }

    private static CreateMovieDto getCreateMovieDto() {
        return new CreateMovieDto("title",
                "description",
                Genre.COMEDY.toString(),
                director.getId(),
                LocalDate.of(2003, 3, 3),
                150);
    }
}
