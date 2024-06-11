package com.example.javalab2.controller;

import com.example.javalab2.dto.UsersDto.UserDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.UserService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/user/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/user/nickname")
    public ResponseEntity<UserDto> getUserByNickName(@RequestParam String nickName) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.findUserByNickName(nickName), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>("All users have been deleted", HttpStatus.OK);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(String.format("User with id %d have been deleted", userId), HttpStatus.OK);
    }


    @DeleteMapping("/user/nickname")
    public ResponseEntity<String> deleteUserByNickname(@RequestParam String nickname) {
        userService.deleteUserByNickName(nickname);
        return new ResponseEntity<>(String.format("User with nickname %s have been deleted", nickname), HttpStatus.OK);
    }

    @DeleteMapping("/user/email")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(String.format("User with email %s have been deleted", email), HttpStatus.OK);
    }
}
