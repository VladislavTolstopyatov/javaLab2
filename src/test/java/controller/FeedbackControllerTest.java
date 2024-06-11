package controller;

import com.example.javalab2.controller.FeedbackController;
import com.example.javalab2.dto.FeedbackDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.FeedBackService;
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
public class FeedbackControllerTest {
    @Mock
    private FeedBackService feedBackService;

    @InjectMocks
    private FeedbackController feedbackController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static final List<FeedbackDto> feedbacks = List.of(
            new FeedbackDto(1L,
                    "filmTitle1",
                    "nickName1",
                    LocalDate.of(2003, 3, 3),
                    "feedback1"),
            new FeedbackDto(2L,
                    "filmTitle",
                    "nickName2",
                    LocalDate.of(2003, 3, 3),
                    "feedback2"));


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
    }

    @Test
    public void getAllFeedbacksTest() throws Exception {
        when(feedBackService.findAllFeedBacks()).thenReturn(feedbacks);

        mockMvc.perform(get("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(feedBackService, times(1)).findAllFeedBacks();
    }

    @Test
    public void getFeedbackByIdTest() throws Exception, EntityNotFoundException {
        final Long id = 1L;
        when(feedBackService.findFeedBackById(id)).thenReturn(feedbacks.get(0));
        mockMvc.perform(get("/feedbacks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("filmTitle1"))
                .andExpect(jsonPath("$.nickName").value("nickName1"))
                .andExpect(jsonPath("$.feedbackDate", containsInAnyOrder(2003, 3, 3)))
                .andExpect(jsonPath("$.feedback").value("feedback1"));
        verify(feedBackService, times(1)).findFeedBackById(id);
    }

    @Test
    public void getFeedbacksByMovieIdTest() throws Exception {
        final Long movieId = 1L;
        when(feedBackService.findFeedBacksByMovieId(movieId)).thenReturn(List.of(feedbacks.get(0)));

        mockMvc.perform(get("/movies/movie/{id}/feedbacks", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        verify(feedBackService, times(1)).findFeedBacksByMovieId(movieId);
    }

    @Test
    public void getFeedbacksByUserIdTest() throws Exception {
        final Long userId = 1L;
        when(feedBackService.findFeedBacksByUserId(userId)).thenReturn(List.of(feedbacks.get(0)));

        mockMvc.perform(get("/feedbacks/userId/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        verify(feedBackService, times(1)).findFeedBacksByUserId(userId);
    }

    @Test
    public void deleteAllFeedbacksTest() throws Exception {
        doNothing().when(feedBackService).deleteAllFeedBacks();
        mockMvc.perform(delete("/feedbacks"))
                .andExpect(status().isOk());
        verify(feedBackService, times(1)).deleteAllFeedBacks();
    }

    @Test
    public void deleteFeedbackByIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(feedBackService).deleteFeedBackById(id);
        mockMvc.perform(delete("/feedbacks/{id}", id))
                .andExpect(status().isOk());
        verify(feedBackService, times(1)).deleteFeedBackById(id);
    }

    @Test
    public void deleteFeedbacksByMovieIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(feedBackService).deleteAllFeedBacksByMovieId(id);
        mockMvc.perform(delete("/feedbacks/movieId/{id}", id))
                .andExpect(status().isOk());
        verify(feedBackService, times(1)).deleteAllFeedBacksByMovieId(id);
    }

    @Test
    public void deleteFeedbacksByUserIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(feedBackService).deleteAllFeedBacksByUserId(id);
        mockMvc.perform(delete("/feedbacks/userId/{id}", id))
                .andExpect(status().isOk());
        verify(feedBackService, times(1)).deleteAllFeedBacksByUserId(id);
    }
}
