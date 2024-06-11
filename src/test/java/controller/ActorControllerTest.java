package controller;

import com.example.javalab2.controller.ActorController;
import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.ActorService;
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

import java.time.LocalDate;
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
public class ActorControllerTest {
    @Mock
    private ActorService actorService;

    @InjectMocks
    private ActorController actorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(actorController).build();
    }

    private static final List<ActorDto> actors = List.of(new ActorDto(1L,
                    "fio",
                    LocalDate.of(2003, 3, 3)),
            new ActorDto(2L,
                    "fio1",
                    LocalDate.of(2003, 3, 3)));


    @Test
    public void getAllDirectorsTest() throws Exception {
        when(actorService.findAllActors()).thenReturn(actors);

        mockMvc.perform(get("/actors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(actorService, times(1)).findAllActors();
    }

    @Test
    public void getActorByIdTest() throws Exception, EntityNotFoundException {
        final Long id = 1L;
        when(actorService.findActorById(id)).thenReturn(actors.get(0));
        mockMvc.perform(get("/actors/actor/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fio").value("fio"))
                .andExpect(jsonPath("$.birthdate", containsInAnyOrder(2003, 3, 3)));
        verify(actorService, times(1)).findActorById(id);
    }

    @Test
    public void getActorByFioTest() throws Exception, EntityNotFoundException {
        final String fio = "fio";
        when(actorService.findActorByFio(fio)).thenReturn(actors.get(0));

        mockMvc.perform(get("/actors/actor/fio").param("fio", fio))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fio").value("fio"))
                .andExpect(jsonPath("$.birthdate", containsInAnyOrder(2003, 3, 3)));
        verify(actorService, times(1)).findActorByFio(fio);
    }


    @Test
    public void getActorsBirthdateTest() throws Exception {
        final LocalDate birthdate = LocalDate.of(2003, 3, 3);
        when(actorService.findActorsByBirthdate(birthdate)).thenReturn(actors);

        mockMvc.perform(get("/actors/birthdate").param("birthdate", String.valueOf(birthdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(actorService, times(1)).findActorsByBirthdate(birthdate);
    }

    @Test
    public void deleteAllActorsTest() throws Exception {
        doNothing().when(actorService).deleteAllActors();
        mockMvc.perform(delete("/actors"))
                .andExpect(status().isOk());
        verify(actorService, times(1)).deleteAllActors();
    }

    @Test
    public void deleteActorByIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(actorService).deleteActorById(id);
        mockMvc.perform(delete("/actors/actor/{id}", id))
                .andExpect(status().isOk());
        verify(actorService, times(1)).deleteActorById(id);
    }

    @Test
    public void deleteActorByFioTest() throws Exception {
        final String fio = "fio";
        doNothing().when(actorService).deleteActorByFio(fio);
        mockMvc.perform(delete("/actors/actor/fio").param("fio", fio))
                .andExpect(status().isOk());
        verify(actorService, times(1)).deleteActorByFio(fio);
    }
}
