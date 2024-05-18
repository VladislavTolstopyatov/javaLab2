package services;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.UserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Role;
import com.example.javalab2.exceptions.EmailAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.NickNameAlreadyExistsException;
import com.example.javalab2.mappers.UserMapper;
import com.example.javalab2.repositories.UserRepository;
import com.example.javalab2.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    public void saveUserWhenNickNameAndEmailUnique() throws EmailAlreadyExistsException, NickNameAlreadyExistsException {
        final User user = getUser();
        final UserDto userDto = getUserDto();

        when(userRepository.findUserByNickName(userDto.getNickName())).thenReturn(null);
        when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        assertThat(userDto).isEqualTo(userService.saveUser(userDto));
    }

    @Test
    public void saveUserWhenNickNameNotUnique() {
        final User user = getUser();
        final UserDto userDto = getUserDto();

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByNickName(userDto.getNickName())).thenReturn(user);

        assertThrows(NickNameAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void saveUserWhenEmailNotUnique() {
        final User user = getUser();
        final UserDto userDto = getUserDto();

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByNickName(userDto.getNickName())).thenReturn(null);
        when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(user);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    void findAllUsersWhenUsersExists() {
        final List<User> userList = List.of(getUser());
        final List<UserDto> userDtos = List.of(getUserDto());

        when(userMapper.toDto(userList)).thenReturn(userDtos);
        when(userRepository.findAll()).thenReturn(userList);

        assertThat(userDtos.get(0)).isEqualTo(userService.getAllUsers().get(0));
    }

    @Test
    void findUserByNickNameWhenUserExists() throws ModelNotFoundException {
        final User user = getUser();
        final UserDto userDto = getUserDto();

        when(userRepository.findUserByNickName(userDto.getNickName())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        assertThat(userDto).isEqualTo(userService.findUserByNickName(userDto.getNickName()));
    }

    @Test
    void findUserByEmailWhenUserNotExists() {
        final UserDto userDto = getUserDto();

        when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(null);
        assertThrows(ModelNotFoundException.class, () -> userService.findUserByEmail(userDto.getEmail()));
    }

    @Test
    void findUserByEmailWhenUserExists() throws ModelNotFoundException {
        final User user = getUser();
        final UserDto userDto = getUserDto();

        when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        assertThat(userDto).isEqualTo(userService.findUserByEmail(userDto.getEmail()));
    }

    @Test
    void findUserByEmailAndPasswordWhenUserExists() throws ModelNotFoundException {
        final User user = getUser();
        final UserDto userDto = getUserDto();
        final String password = "password";

        when(userRepository.findUserByPasswordAndEmail(password, userDto.getEmail())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        assertThat(userDto).isEqualTo(userService.findUserByPasswordAndEmail(password, userDto.getEmail()));
    }

    @Test
    void findUserByEmailAndPasswordWhenUserNotExists() {
        final UserDto userDto = getUserDto();
        final String password = "password";

        when(userRepository.findUserByPasswordAndEmail(password, userDto.getEmail())).thenReturn(null);
        assertThrows(ModelNotFoundException.class, () -> userService.findUserByPasswordAndEmail(password, userDto.getEmail()));
    }

    @Test
    void findUserByNickNameWhenUserNotExists() {
        final UserDto userDto = getUserDto();

        when(userRepository.findUserByNickName(userDto.getNickName())).thenReturn(null);
        assertThrows(ModelNotFoundException.class, () -> userService.findUserByNickName(userDto.getNickName()));
    }

    @Test
    void findUserByNotValidId() {
        final Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> userService.findUserById(id));
    }

    @Test
    void findUserByIdWhenUserNotExists() {
        final Long id = getUserDto().getId();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class, () -> userService.findUserById(id));
    }

    @Test
    void findUserByIdWhenUserExists() throws ModelNotFoundException {
        final Long id = getUserDto().getId();
        final User user = getUser();
        final UserDto userDto = getUserDto();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        assertThat(userDto).isEqualTo(userService.findUserById(id));
    }

    @Test
    void deleteAllUsers() {
        userService.deleteAllUsers();
        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void deleteUserByValidId() {
        final Long id = 1L;
        userService.deleteUserById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteUserByNickName() {
        final String nickName = "nickName";
        userService.deleteUserByNickName(nickName);
        verify(userRepository, times(1)).deleteUserByNickName(nickName);
    }

    @Test
    void deleteUserByEmail() {
        final String email = "email";
        userService.deleteUserByEmail(email);
        verify(userRepository, times(1)).deleteUserByEmail(email);
    }

    @Test
    void deleteUserByNotValidId() {
        final Long notValidId = -1L;
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(notValidId));
    }

    private static UserDto getUserDto() {
        return new UserDto(1L,
                "email",
                "nickName",
                Collections.emptyList());
    }


    private static User getUser() {
        return new User(1L,
                "email",
                "password",
                "nickName",
                Role.USER,
                Collections.emptyList());
    }
}
