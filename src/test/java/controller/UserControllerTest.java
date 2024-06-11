package controller;

import com.example.javalab2.controller.UserController;
import com.example.javalab2.dto.UsersDto.UserDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.UserService;
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

import java.util.Collections;
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
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private static final List<UserDto> users = List.of(
            new UserDto(1L,
                    "email1",
                    "nickName1",
                    Collections.emptyList()),

            new UserDto(2L,
                    "email2",
                    "nickName2",
                    Collections.emptyList()));

    @Test
    public void getAllUsersTest() throws Exception {
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void getUserByIdTest() throws Exception, EntityNotFoundException {
        final Long id = 1L;
        when(userService.findUserById(id)).thenReturn(users.get(0));
        mockMvc.perform(get("/users/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email1"))
                .andExpect(jsonPath("$.nickName").value("nickName1"))
                .andExpect(jsonPath("$.feedbackList", Matchers.hasSize(0)));
        verify(userService, times(1)).findUserById(id);
    }

    @Test
    public void getUserByEmailTest() throws Exception, EntityNotFoundException {
        final String email = "email1";
        when(userService.findUserByEmail(email)).thenReturn(users.get(0));
        mockMvc.perform(get("/users/user/email").param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email1"))
                .andExpect(jsonPath("$.nickName").value("nickName1"))
                .andExpect(jsonPath("$.feedbackList", Matchers.hasSize(0)));
        verify(userService, times(1)).findUserByEmail(email);
    }

    @Test
    public void getUserByNickNameTest() throws Exception, EntityNotFoundException {
        final String nickName = "nickName1";
        when(userService.findUserByNickName(nickName)).thenReturn(users.get(0));
        mockMvc.perform(get("/users/user/nickname").param("nickName", nickName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email1"))
                .andExpect(jsonPath("$.nickName").value(nickName))
                .andExpect(jsonPath("$.feedbackList", Matchers.hasSize(0)));
        verify(userService, times(1)).findUserByNickName(nickName);
    }


    @Test
    public void deleteAllUsersTest() throws Exception {
        doNothing().when(userService).deleteAllUsers();
        mockMvc.perform(delete("/users"))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteAllUsers();
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        final Long id = 1L;
        doNothing().when(userService).deleteUserById(id);
        mockMvc.perform(delete("/users/user/{id}", id))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserById(id);
    }

    @Test
    public void deleteUserByNickNameTest() throws Exception {
        final String nickName = "nickName";
        doNothing().when(userService).deleteUserByNickName(nickName);
        mockMvc.perform(delete("/users/user/nickname").param("nickname", nickName))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserByNickName(nickName);
    }

    @Test
    public void deleteUserByEmailTest() throws Exception {
        final String email = "email";
        doNothing().when(userService).deleteUserByEmail(email);
        mockMvc.perform(delete("/users/user/email", email).param("email", email))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserByEmail(email);
    }
}
