package controller;

import com.example.javalab2.controller.MovieController;
import com.example.javalab2.dto.MoviesDto.MovieDto;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    private static final List<MovieDto> movies = List.of(new MovieDto(1L,
                    "title1",
                    "description1",
                    "COMEDY",
                    "name surname patronymic",
                    LocalDate.of(2003, 3, 3),
                    150,
                    Collections.emptyList(),
                    Collections.emptyList()),
            new MovieDto(2L,
                    "title2",
                    "description2",
                    "DRAMA",
                    "name surname patronymic",
                    LocalDate.of(2003, 3, 3),
                    150,
                    Collections.emptyList(),
                    Collections.emptyList()));

    @Test
    public void getAllMoviesTest() throws Exception {
        when(movieService.findAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(movieService, times(1)).findAllMovies();
    }

    @Test
    public void getMovieByIdTest() throws Exception, EntityNotFoundException {
        final Long id = 1L;
        when(movieService.findMovieById(id)).thenReturn(movies.get(0));
        mockMvc.perform(get("/movies/movie/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.genre").value("COMEDY"))
                .andExpect(jsonPath("$.dateOfRelease", containsInAnyOrder(2003, 3, 3)))
                .andExpect(jsonPath("$.directorFio").value("name surname patronymic"))
                .andExpect(jsonPath("$.duration").value(150))
                .andExpect(jsonPath("$.feedbackDtoList", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.actorDtoList", Matchers.hasSize(0)));
        verify(movieService, times(1)).findMovieById(id);
    }

    @Test
    public void getMovieByDescriptionTest() throws Exception, EntityNotFoundException {
        final String description = "description1";
        when(movieService.findMovieByDescription(description)).thenReturn(movies.get(0));

        mockMvc.perform(get("/movies/movie/description").param("description", description))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.genre").value("COMEDY"))
                .andExpect(jsonPath("$.dateOfRelease", containsInAnyOrder(2003, 3, 3)))
                .andExpect(jsonPath("$.directorFio").value("name surname patronymic"))
                .andExpect(jsonPath("$.duration").value(150))
                .andExpect(jsonPath("$.feedbackDtoList", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.actorDtoList", Matchers.hasSize(0)));
        verify(movieService, times(1)).findMovieByDescription(description);
    }

    @Test
    public void getMovieByTitleTest() throws Exception, EntityNotFoundException {
        final String title = "title1";
        when(movieService.findMovieByTitle(title)).thenReturn(movies.get(0));

        mockMvc.perform(get("/movies/movie/title").param("title", title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.genre").value("COMEDY"))
                .andExpect(jsonPath("$.dateOfRelease", containsInAnyOrder(2003, 3, 3)))
                .andExpect(jsonPath("$.directorFio").value("name surname patronymic"))
                .andExpect(jsonPath("$.duration").value(150))
                .andExpect(jsonPath("$.feedbackDtoList", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.actorDtoList", Matchers.hasSize(0)));
        verify(movieService, times(1)).findMovieByTitle(title);
    }


    @Test
    public void getMoviesByDurationTest() throws Exception {
        final Integer duration = 150;
        when(movieService.findMoviesByDuration(duration)).thenReturn(movies);

        mockMvc.perform(get("/movies/duration").param("duration", String.valueOf(duration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(movieService, times(1)).findMoviesByDuration(duration);
    }

    @Test
    public void getMoviesByGenreTest() throws Exception {
        final Genre genre = Genre.COMEDY;
        when(movieService.findMoviesByGenre(genre)).thenReturn(List.of(movies.get(0)));

        mockMvc.perform(get("/movies/genre").param("genre", String.valueOf(genre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        verify(movieService, times(1)).findMoviesByGenre(genre);
    }

    @Test
    public void getMoviesByDirectorIdTest() throws Exception {
        final Long directorId = 1L;
        when(movieService.findMoviesByDirectorId(directorId)).thenReturn(movies);

        mockMvc.perform(get("/movies/directorsId/{id}", directorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(movieService, times(1)).findMoviesByDirectorId(directorId);
    }


    @Test
    public void getMoviesByDateOfReleaseTest() throws Exception {
        final LocalDate dateOfRelease = LocalDate.of(2003, 3, 3);
        when(movieService.findMoviesByDateOfRelease(dateOfRelease)).thenReturn(movies);

        mockMvc.perform(get("/movies/dateOfRelease").param("dateOfRelease", String.valueOf(dateOfRelease)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(movieService, times(1)).findMoviesByDateOfRelease(dateOfRelease);
    }

    @Test
    public void deleteAllMoviesTest() throws Exception {
        doNothing().when(movieService).deleteAllMovies();
        mockMvc.perform(delete("/movies"))
                .andExpect(status().isOk());
        verify(movieService, times(1)).deleteAllMovies();
    }

    @Test
    public void deleteMovieByIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(movieService).deleteMovieById(id);
        mockMvc.perform(delete("/movies/movie/{id}", id))
                .andExpect(status().isOk());
        verify(movieService, times(1)).deleteMovieById(id);
    }

    @Test
    public void deleteMovieByTitleTest() throws Exception {
        final String title = "title";
        doNothing().when(movieService).deleteMovieByTitle(title);
        mockMvc.perform(delete("/movies/movie/title").param("title", title))
                .andExpect(status().isOk());
        verify(movieService, times(1)).deleteMovieByTitle(title);
    }

    @Test
    public void deleteMovieByDirectorIdTest() throws Exception {
        final Long directorId = 1L;
        doNothing().when(movieService).deleteMoviesByDirectorId(directorId);
        mockMvc.perform(delete("/movies/directorId/{id}", directorId))
                .andExpect(status().isOk());
        verify(movieService, times(1)).deleteMoviesByDirectorId(directorId);
    }
}
