package mappers;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.UsersDto.CreateUserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Role;
import com.example.javalab2.mappers.CreateUserDtoMapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@SpringBootTest(classes = JavaLab2Application.class)
@ExtendWith(SpringExtension.class)
public class CreateUserDtoMapperTest {
    @Autowired
    private CreateUserDtoMapper createUserDtoMapper;

    private static final CreateUserDto createUserDto = new CreateUserDto("email",
            "nickname",
            "password");

    private static final User user = new User(1L, "email",
            "password",
            "nickname",
            Role.USER,
            Collections.emptyList());

    @Test
    void fromEntityToDtoTest() {
        CreateUserDto createUserDto = createUserDtoMapper.toDto(user);
        assertTrue(createUserDto.getEmail().equals(user.getEmail()) &&
                createUserDto.getNickName().equals(user.getNickName()) &&
                createUserDto.getPassword().equals(user.getPassword()));
    }

    @Test
    void fromDtoToEntityTest() {
        User user = createUserDtoMapper.toEntity(createUserDto);
        assertTrue(user.getNickName().equals(createUserDto.getNickName()) &&
                user.getEmail().equals(createUserDto.getEmail()) &&
                user.getPassword().equals(createUserDto.getPassword()));
    }
}
