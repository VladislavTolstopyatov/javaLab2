package com.example.javalab2.controller;

import com.example.javalab2.dto.UsersDto.UserDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User Controller",description = "Обрабатывает запросы, связанные с пользователем")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Получить пользователя по id")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Получить пользователя по email")
    @GetMapping("/user/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Получить пользователя по нику")
    @GetMapping("/user/nickname")
    public ResponseEntity<UserDto> getUserByNickName(@RequestParam String nickName) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.findUserByNickName(nickName), HttpStatus.OK);
    }

    @Operation(summary = "Удалить всех пользователей")
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>("All users have been deleted", HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя по id")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(String.format("User with id %d have been deleted", userId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя по нику")
    @DeleteMapping("/user/nickname")
    public ResponseEntity<String> deleteUserByNickname(@RequestParam String nickname) {
        userService.deleteUserByNickName(nickname);
        return new ResponseEntity<>(String.format("User with nickname %s have been deleted", nickname), HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя по email")
    @DeleteMapping("/user/email")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(String.format("User with email %s have been deleted", email), HttpStatus.OK);
    }
}
