package mappers;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.UserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Role;
import com.example.javalab2.mappers.FeedBackMapper;
import com.example.javalab2.mappers.UserMapper;
import com.example.javalab2.repositories.FeedBackRepository;
import com.example.javalab2.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = JavaLab2Application.class)
@ExtendWith(SpringExtension.class)
public class UserMapperTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FeedBackRepository feedBackRepository;

    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private UserMapper userMapper;


    private static final User user = new User(1L,
            "email",
            "password",
            "nickName",
            Role.USER,
            Collections.emptyList());

    private static final UserDto userDto = new UserDto(1L,
            "email",
            "nickName",
            Collections.emptyList());

    @Test
    public void fromEntityToDto() {
        final UserDto userDto = userMapper.toDto(user);
        assertTrue(userDto.getId().equals(user.getId()) &&
                userDto.getEmail().equals(user.getEmail()) &&
                userDto.getNickName().equals(user.getNickName()) &&
                userDto.getFeedbackList().size() == user.getFeedbacks().size() &&
                userDto.getFeedbackList().containsAll(feedBackMapper.toDto(user.getFeedbacks())));
    }

    @Test
    public void fromDtoToEntity() {
        when(userRepository.findUserByNickName(userDto.getNickName())).thenReturn(user);
        final User user = userMapper.toEntity(userDto);
        assertTrue(userDto.getId().equals(user.getId()) &&
                userDto.getEmail().equals(user.getEmail()) &&
                userDto.getNickName().equals(user.getNickName()) &&
                userDto.getFeedbackList().size() == user.getFeedbacks().size() &&
                userDto.getFeedbackList().containsAll(feedBackMapper.toDto(user.getFeedbacks())));
    }
}
