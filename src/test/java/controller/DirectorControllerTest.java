package controller;

import com.example.javalab2.controller.DirectorController;
import com.example.javalab2.dto.DirectorDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.DirectorService;
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
public class DirectorControllerTest {
    @Mock
    private DirectorService directorService;

    @InjectMocks
    private DirectorController directorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static final List<DirectorDto> directors = List.of(new DirectorDto(1L,
                    "name",
                    "surname",
                    "patronymic",
                    LocalDate.of(2003, 3, 3),
                    Boolean.FALSE,
                    Collections.emptyList()),
            new DirectorDto(2L,
                    "name",
                    "surname",
                    "patronymic",
                    LocalDate.of(2003, 3, 3),
                    Boolean.TRUE,
                    Collections.emptyList()));

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(directorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllDirectorsTest() throws Exception {
        when(directorService.findAllDirectors()).thenReturn(directors);

        mockMvc.perform(get("/directors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(directorService, times(1)).findAllDirectors();
    }

    @Test
    public void getDirectorByIdTest() throws Exception, EntityNotFoundException {
        final Long id = 1L;
        when(directorService.findDirectorById(id)).thenReturn(directors.get(0));
        mockMvc.perform(get("/directors/director/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.surname").value("surname"))
                .andExpect(jsonPath("$.patronymic").value("patronymic"))
                .andExpect(jsonPath("$.birthdate", containsInAnyOrder(2003, 3, 3)))
                .andExpect(jsonPath("$.oscar").value(Boolean.FALSE))
                .andExpect(jsonPath("$.movieDtoList", Matchers.hasSize(0)));
        verify(directorService, times(1)).findDirectorById(id);
    }

    @Test
    public void getDirectorsByNameTest() throws Exception {
        final String name = "name";
        when(directorService.findDirectorsByName(name)).thenReturn(directors);

        mockMvc.perform(get("/directors/name").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(directorService, times(1)).findDirectorsByName(name);
    }

    @Test
    public void getDirectorsBySurnameTest() throws Exception {
        final String surname = "surname";
        when(directorService.findDirectorsBySurname(surname)).thenReturn(directors);

        mockMvc.perform(get("/directors/surname").param("surname", surname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(directorService, times(1)).findDirectorsBySurname(surname);
    }

    @Test
    public void getDirectorsByPatronymicTest() throws Exception {
        final String patronymic = "patronymic";
        when(directorService.findDirectorsByPatronymic(patronymic)).thenReturn(directors);

        mockMvc.perform(get("/directors/patronymic").param("patronymic", patronymic))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(directorService, times(1)).findDirectorsByPatronymic(patronymic);
    }

    @Test
    public void getDirectorsByOscarTest() throws Exception {
        final Boolean oscar = Boolean.TRUE;
        when(directorService.findDirectorsByOscar(oscar)).thenReturn(List.of(directors.get(0)));

        mockMvc.perform(get("/directors/oscar").param("oscar", String.valueOf(oscar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        verify(directorService, times(1)).findDirectorsByOscar(oscar);
    }

    @Test
    public void getDirectorsBirthdateTest() throws Exception {
        final LocalDate birthdate = LocalDate.of(2003, 3, 3);
        when(directorService.findDirectorsByBirthdate(birthdate)).thenReturn(directors);

        mockMvc.perform(get("/directors/birthdate").param("birthdate", String.valueOf(birthdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(directorService, times(1)).findDirectorsByBirthdate(birthdate);
    }

    @Test
    public void deleteAllDirectorsTest() throws Exception {
        doNothing().when(directorService).deleteAllDirectors();
        mockMvc.perform(delete("/directors"))
                .andExpect(status().isOk());
        verify(directorService, times(1)).deleteAllDirectors();
    }

    @Test
    public void deleteDirectorByIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(directorService).deleteDirectorById(id);
        mockMvc.perform(delete("/directors/director/{id}", id))
                .andExpect(status().isOk());
        verify(directorService, times(1)).deleteDirectorById(id);
    }

//    @Test
//    public void saveDirectorTest() throws Exception {
//        final DirectorDto directorDto = directors.get(0);
//        final String jsonDirector = objectMapper.writeValueAsString(directorDto);
//
//        when(directorService.saveDirector(directors.get(0))).thenReturn(directorDto);
//        mockMvc.perform(post("/directors", jsonDirector))
//                .andExpect(status().isCreated());
//
//        verify(directorService, times(1)).saveDirector(directorDto);
//    }
}
