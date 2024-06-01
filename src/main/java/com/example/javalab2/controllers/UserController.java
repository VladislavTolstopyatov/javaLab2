package com.example.javalab2.controllers;

import com.example.javalab2.dto.UsersDto.CreateUserDto;
import com.example.javalab2.dto.UsersDto.UserDto;
import com.example.javalab2.exceptions.EmailAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.NickNameAlreadyExistsException;
import com.example.javalab2.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/save")
    public ResponseEntity<String> saveUser(@RequestBody CreateUserDto createUserDto) throws EmailAlreadyExistsException, NickNameAlreadyExistsException {
        userService.saveUser(createUserDto);
        return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) throws ModelNotFoundException {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/users/emails/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) throws ModelNotFoundException {
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/users/nicknames/{nickname}")
    public ResponseEntity<UserDto> getUserByNickName(@PathVariable("nickname") String nickName) throws ModelNotFoundException {
        return new ResponseEntity<>(userService.findUserByNickName(nickName), HttpStatus.OK);
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>("All users have been deleted", HttpStatus.OK);
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(String.format("User with id %d have been deleted", userId), HttpStatus.OK);
    }

    @DeleteMapping("/users/deleteByNickname/{nickname}")
    public ResponseEntity<String> deleteUserByNickname(@PathVariable("nickname") String nickname) {
        userService.deleteUserByNickName(nickname);
        return new ResponseEntity<>(String.format("User with nickname %s have been deleted", nickname), HttpStatus.OK);
    }

    @DeleteMapping("/users/deleteByEmail/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable("email") String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(String.format("User with email %s have been deleted", email), HttpStatus.OK);
    }
}
