package com.example.javalab2.repositories;

import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private UserRepository userRepository;

    @Test
    void testThatConnectionEstablished() {
        assertThat(postgresSQLContainer.isCreated()).isTrue();
        assertThat(postgresSQLContainer.isRunning()).isTrue();
    }


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void saveUserTest() { // equals to test findById
        User user = userRepository.save(new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList()));

        User userFromRepo = userRepository.findById(user.getId()).get();
        assertThat(user).isEqualTo(userFromRepo);
    }

    @Test
    void findAllUsersTest() {
        List<User> users = List.of(new User(null,
                        "test@mail.ru",
                        "password",
                        "nickName",
                        Role.USER, Collections.emptyList()),
                new User(null,
                        "test2@mail.ru",
                        "password",
                        "nickname",
                        Role.USER, Collections.emptyList()));

        userRepository.saveAll(users);
        List<User> usersFromRepo = userRepository.findAll();
        assertTrue(usersFromRepo.size() == users.size() &&
                usersFromRepo.containsAll(users) && users.containsAll(usersFromRepo));
    }

    @Test
    void deleteUserByIdTest() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        User userFromRepo = userRepository.save(user);
        userRepository.deleteById(userFromRepo.getId());
        assertThat(userRepository.findById(userFromRepo.getId()).isEmpty()).isTrue();
    }

    @Test
    void deleteAllUsersTest() {
        List<User> users = List.of(new User(null,
                        "test@mail.ru",
                        "password",
                        "nickName",
                        Role.USER, Collections.emptyList()),
                new User(null,
                        "test2@mail.ru",
                        "password",
                        "nickname",
                        Role.USER, Collections.emptyList()));

        userRepository.saveAll(users);
        userRepository.deleteAll();
        List<User> usersFromRepo = (List<User>) userRepository.findAll();
        assertThat(usersFromRepo.isEmpty()).isTrue();
    }

    @Test
    void findUserByNickName() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        userRepository.save(user);
        User userFromRepo = userRepository.findUserByNickName(user.getNickName());
        assertThat(user).isEqualTo(userFromRepo);
    }

    @Test
    void findUserByPasswordAndEmail() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        userRepository.save(user);
        User userFromRepo = userRepository.findUsersByPasswordAndEmail(user.getPassword(), user.getEmail());
        assertThat(user).isEqualTo(userFromRepo);
    }

    @Test
    void findUserByEmail() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        userRepository.save(user);
        User userFromRepo = userRepository.findUserByEmail(user.getEmail());
        assertThat(user).isEqualTo(userFromRepo);
    }

    @Test
    void deleteUserByNickName() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        userRepository.save(user);
        userRepository.deleteUserByNickName(user.getNickName());
        assertThat(userRepository.findUserByNickName(user.getNickName())).isNull();
    }

    @Test
    void deleteUserByEmail() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        userRepository.save(user);
        userRepository.deleteUserByEmail(user.getEmail());
        assertThat(userRepository.findUserByEmail(user.getNickName())).isNull();
    }

    @Test
    void updateUserTest() {
        User user = new User(null,
                "test",
                "test",
                "test",
                Role.USER, Collections.emptyList());

        userRepository.save(user);
        user.setNickName("newNickName");
        userRepository.save(user);
        assertThat(user).isEqualTo(userRepository.findUserByNickName("newNickName"));
    }
}