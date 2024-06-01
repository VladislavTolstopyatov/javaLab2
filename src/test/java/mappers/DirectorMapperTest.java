package mappers;


import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.DirectorDto;
import com.example.javalab2.entities.Director;
import com.example.javalab2.mappers.DirectorMapper;
import com.example.javalab2.mappers.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JavaLab2Application.class)
public class DirectorMapperTest {
    @Autowired
    private DirectorMapper directorMapper;

    @Autowired
    private MovieMapper movieMapper;

    private static final Director director = new Director(1L,
            "name",
            "surname",
            "patronymic",
            LocalDate.of(2003, 3, 3),
            Boolean.TRUE,
            Collections.emptyList());

    private static final DirectorDto directorDto = new DirectorDto(1L,
            "name",
            "surname",
            "patronymic",
            LocalDate.of(2003, 3, 3),
            Boolean.TRUE,
            Collections.emptyList());

    @Test
    public void fromEntityToDto() {
        final DirectorDto directorDto = directorMapper.toDto(director);

        assertTrue(directorDto.getId().equals(director.getId()) &&
                directorDto.getName().equals(director.getName()) &&
                directorDto.getSurname().equals(director.getSurname()) &&
                directorDto.getPatronymic().equals(director.getPatronymic()) &&
                directorDto.getBirthdate().equals(director.getBirthdate()) &&
                directorDto.getOscar().equals(director.getOscar()) &&
                directorDto.getMovieDtoList().size() == director.getMovies().size() &&
                directorDto.getMovieDtoList().containsAll(movieMapper.toDto(director.getMovies())));
    }

    @Test
    public void fromDtoToEntity() {
        final Director director = directorMapper.toEntity(directorDto);

        assertTrue(directorDto.getId().equals(director.getId()) &&
                directorDto.getName().equals(director.getName()) &&
                directorDto.getSurname().equals(director.getSurname()) &&
                directorDto.getPatronymic().equals(director.getPatronymic()) &&
                directorDto.getBirthdate().equals(director.getBirthdate()) &&
                directorDto.getOscar().equals(director.getOscar()) &&
                directorDto.getMovieDtoList().size() == director.getMovies().size() &&
                directorDto.getMovieDtoList().containsAll(movieMapper.toDto(director.getMovies())));
    }
}
