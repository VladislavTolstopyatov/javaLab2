package service;

import com.example.javalab2.dto.FeedbackDto;
import com.example.javalab2.entity.Feedback;
import com.example.javalab2.entity.Movie;
import com.example.javalab2.entity.User;
import com.example.javalab2.entity.enums.Genre;
import com.example.javalab2.entity.enums.Role;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.mapper.FeedBackMapper;
import com.example.javalab2.repository.FeedBackRepository;
import com.example.javalab2.service.FeedBackService;
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
public class FeedBackServiceTest {
    @Mock
    private FeedBackRepository feedBackRepository;
    @Mock
    private FeedBackMapper feedBackMapper;
    @InjectMocks
    private FeedBackService feedBackService;

    private static final Movie movie = new Movie(1L,
            "title",
            "description",
            Genre.COMEDY,
            LocalDate.of(2003, 3, 3),
            150,
            null,
            Collections.emptyList(),
            Collections.emptyList());

    private static final User user = new User(1L,
            "email",
            "password",
            "nickName",
            Role.USER,
            Collections.emptyList());

    @Test
    public void saveFeedBack() {
        final Feedback feedback = getFeedback();
        final FeedbackDto feedbackDto = getFeedbackDto();

        when(feedBackRepository.save(feedback)).thenReturn(feedback);
        when(feedBackMapper.toEntity(feedbackDto)).thenReturn(feedback);
        when(feedBackMapper.toDto(feedback)).thenReturn(feedbackDto);
        assertThat(feedbackDto).isEqualTo(feedBackService.saveFeedBack(feedbackDto));
    }

    @Test
    void deleteFeedBackByInvalidId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.deleteFeedBackById(invalidId));
    }

    @Test
    void deleteFeedBackByValidId() {
        final Long validId = 1L;
        feedBackService.deleteFeedBackById(validId);
        verify(feedBackRepository, times(1)).deleteById(validId);
    }

    @Test
    void findAllFeedBacks() {
        final List<Feedback> feedbackList = List.of(getFeedback());
        final List<FeedbackDto> feedbackDtos = List.of(getFeedbackDto());

        when(feedBackMapper.toDto(feedbackList)).thenReturn(feedbackDtos);
        when(feedBackRepository.findAll()).thenReturn(feedbackList);

        assertThat(feedbackDtos.get(0)).isEqualTo(feedBackService.findAllFeedBacks().get(0));
    }

    @Test
    void deleteAllFeedBacks() {
        feedBackService.deleteAllFeedBacks();
        verify(feedBackRepository, times(1)).deleteAll();
    }

    @Test
    void findAllByInvalidUserId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.findFeedBacksByUserId(invalidId));
    }

    @Test
    void findAllByValidUserIdWhenFeedbacksExists() {
        final Long userId = user.getId();
        final List<Feedback> feedbacks = List.of(getFeedback());
        final List<FeedbackDto> feedbackDtos = List.of(getFeedbackDto());

        when(feedBackRepository.findFeedbacksByUserId(userId)).thenReturn(feedbacks);
        when(feedBackMapper.toDto(feedbacks)).thenReturn(feedbackDtos);
        assertThat(feedbackDtos.get(0)).isEqualTo(feedBackService.findFeedBacksByUserId(userId).get(0));
    }

    @Test
    void findAllByInvalidMovieId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.findFeedBacksByMovieId(invalidId));
    }

    @Test
    void findAllByValidMovieIdWhenFeedbacksExists() {
        final Long movieId = movie.getId();
        final List<Feedback> feedbacks = List.of(getFeedback());
        final List<FeedbackDto> feedbackDtos = List.of(getFeedbackDto());

        when(feedBackRepository.findFeedbacksByMovieId(movieId)).thenReturn(feedbacks);
        when(feedBackMapper.toDto(feedbacks)).thenReturn(feedbackDtos);
        assertThat(feedbackDtos.get(0)).isEqualTo(feedBackService.findFeedBacksByMovieId(movieId).get(0));
    }

    @Test
    void findAllByInvalidMovieIdAndUserId() {
        final Long invalidUserId = -1L;
        final Long invalidMovieId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.
                findFeedBacksByMovieIdAndUserId(invalidMovieId, invalidUserId));
    }

    @Test
    void findAllByValidMovieIdAndUserIdWhenFeedbacksExists() {
        final Long movieId = movie.getId();
        final Long userId = user.getId();
        final List<Feedback> feedbacks = List.of(getFeedback());
        final List<FeedbackDto> feedbackDtos = List.of(getFeedbackDto());

        when(feedBackRepository.findFeedbacksByUserIdAndMovieId(movieId, userId)).thenReturn(feedbacks);
        when(feedBackMapper.toDto(feedbacks)).thenReturn(feedbackDtos);
        assertThat(feedbackDtos.get(0)).isEqualTo(feedBackService.findFeedBacksByMovieIdAndUserId(movieId, userId).get(0));
    }

    @Test
    void findFeedbackByInvalidId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.findFeedBackById(invalidId));
    }

    @Test
    void findFeedBackByIdWhenFeedBackExists() throws EntityNotFoundException {
        final Feedback feedback = getFeedback();
        final FeedbackDto feedbackDto = getFeedbackDto();

        when(feedBackRepository.findById(feedbackDto.getId())).thenReturn(Optional.of(feedback));
        when(feedBackMapper.toDto(feedback)).thenReturn(feedbackDto);
        assertThat(feedbackDto).isEqualTo(feedBackService.findFeedBackById(feedbackDto.getId()));
    }

    @Test
    void findFeedBackByIdWhenFeedbackNotExists() {
        final FeedbackDto feedbackDto = getFeedbackDto();
        when(feedBackRepository.findById(feedbackDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> feedBackService.findFeedBackById(feedbackDto.getId()));
    }

    @Test
    void deleteFeedBackByInvalidUserId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.deleteAllFeedBacksByUserId(invalidId));
    }

    @Test
    void deleteFeedBackByValidUserId() {
        final Long validId = 1L;
        feedBackService.deleteAllFeedBacksByUserId(validId);
        verify(feedBackRepository, times(1)).deleteFeedbacksByUserId(validId);
    }

    @Test
    void deleteFeedBackByInvalidMovieId() {
        final Long invalidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> feedBackService.deleteAllFeedBacksByMovieId(invalidId));
    }

    @Test
    void deleteFeedBackByValidMovieId() {
        final Long validId = 1L;
        feedBackService.deleteAllFeedBacksByMovieId(validId);
        verify(feedBackRepository, times(1)).deleteFeedbacksByMovieId(validId);
    }


    private static FeedbackDto getFeedbackDto() {
        return new FeedbackDto(1L,
                "filmTitle",
                "nickName",
                LocalDate.of(2003, 3, 3),
                "feedback");
    }


    private static Feedback getFeedback() {
        return new Feedback(1L,
                movie,
                user,
                LocalDate.of(2003, 3, 3),
                "feedback");
    }
}
