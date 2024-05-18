package mappers;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.FeedBackDto;
import com.example.javalab2.entities.Feedback;
import com.example.javalab2.entities.Movie;
import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Genre;
import com.example.javalab2.entities.enums.Role;
import com.example.javalab2.mappers.FeedBackMapper;
import com.example.javalab2.repositories.MovieRepository;
import com.example.javalab2.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class FeedBackMapperTest {

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private FeedBackMapper feedBackMapper;

    private static final Movie movie = new Movie(1L,
            "title",
            "description",
            Genre.COMEDY, LocalDate.of(2003, 3, 3),
            150,
            null,
            Collections.emptyList());

    private static final User user = new User(1L,
            "email",
            "password",
            "nickName",
            Role.USER,
            Collections.emptyList());


    private static final Feedback feedback = new Feedback(1L,
            movie,
            user,
            LocalDate.of(2003, 3, 3),
            "feedback");

    private static final FeedBackDto feedbackDto = new FeedBackDto(1L,
            "title",
            "nickName",
            LocalDate.of(2003, 3, 3),
            "feedback");


    @Test
    public void fromEntityToDtoTest() {
        final FeedBackDto feedBackDto = feedBackMapper.toDto(feedback);
        assertTrue(feedBackDto.getId().equals(feedback.getId()) &&
                feedBackDto.getTitle().equals(feedback.getMovie().getTitle()) &&
                feedBackDto.getNickName().equals(feedback.getUser().getNickName()) &&
                feedBackDto.getFeedbackDate().equals(feedback.getFeedbackDate()) &&
                feedBackDto.getFeedback().equals(feedback.getFeedback()));
    }

    // ?????
    @Test
    public void fromDtoToEntityTest() {
        when(movieRepository.findMovieByTitle(feedbackDto.getTitle())).thenReturn(movie);
        when(userRepository.findUserByNickName(feedbackDto.getNickName())).thenReturn(user);
        final Feedback feedback = feedBackMapper.toEntity(feedbackDto);
        assertTrue(feedbackDto.getId().equals(feedback.getId()) &&
                feedbackDto.getTitle().equals(feedback.getMovie().getTitle()) &&
                feedbackDto.getNickName().equals(feedback.getUser().getNickName()) &&
                feedbackDto.getFeedbackDate().equals(feedback.getFeedbackDate()) &&
                feedbackDto.getFeedback().equals(feedback.getFeedback()));
    }
}
