package mapper;

import com.example.javalab2.JavaLab2Application;
import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entity.Actor;
import com.example.javalab2.mapper.ActorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = JavaLab2Application.class)
@ExtendWith(SpringExtension.class)
public class ActorMapperTest {

    @Autowired
    private ActorMapper actorMapper;

    @Test
    public void fromEntityToDtoTest() {
        final Actor actor = new Actor(1L, "fio", LocalDate.of(2003, 3, 3));
        final ActorDto actorDto = actorMapper.toDto(actor);
        assertTrue(actor.getId().equals(actorDto.getId()) &&
                actor.getFio().equals(actorDto.getFio()) &&
                actor.getBirthdate().equals(actorDto.getBirthdate()));
    }

    @Test
    public void fromDtoToEntityTest() {
        final ActorDto actorDto = new ActorDto(1L, "fio", LocalDate.of(2003, 3, 3));
        final Actor actor = actorMapper.toEntity(actorDto);
        assertTrue(actor.getId().equals(actorDto.getId()) &&
                actor.getFio().equals(actorDto.getFio()) &&
                actor.getBirthdate().equals(actorDto.getBirthdate()));
    }

    @Test
    public void fromEntitiesToDtoListTest() {
        final List<Actor> actorList = List.of(
                new Actor(1L, "fio1", LocalDate.of(2003, 3, 3)),
                new Actor(2L, "fio2", LocalDate.of(2003, 3, 3)));

        final List<ActorDto> actorDtos = actorMapper.toDto(actorList);
        assertTrue(actorList.get(0).getId().equals(actorDtos.get(0).getId()) &&
                actorList.get(0).getFio().equals(actorDtos.get(0).getFio()) &&
                actorList.get(0).getBirthdate().equals(actorDtos.get(0).getBirthdate()) &&
                actorList.get(1).getId().equals(actorDtos.get(1).getId()) &&
                actorList.get(1).getFio().equals(actorDtos.get(1).getFio()) &&
                actorList.get(1).getBirthdate().equals(actorDtos.get(1).getBirthdate()) &&
                actorList.size() == actorDtos.size());
    }

    @Test
    public void fromDtosToEntityListTest() {
        final List<ActorDto> actorDtoList = List.of(
                new ActorDto(1L, "fio1", LocalDate.of(2003, 3, 3)),
                new ActorDto(2L, "fio2", LocalDate.of(2003, 3, 3)));

        final List<Actor> actorList = actorMapper.toEntities(actorDtoList);
        assertTrue(actorList.get(0).getId().equals(actorDtoList.get(0).getId()) &&
                actorList.get(0).getFio().equals(actorDtoList.get(0).getFio()) &&
                actorList.get(0).getBirthdate().equals(actorDtoList.get(0).getBirthdate()) &&
                actorList.get(1).getId().equals(actorDtoList.get(1).getId()) &&
                actorList.get(1).getFio().equals(actorDtoList.get(1).getFio()) &&
                actorList.get(1).getBirthdate().equals(actorDtoList.get(1).getBirthdate()) &&
                actorList.size() == actorDtoList.size());
    }
}
