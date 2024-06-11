package com.example.javalab2.controller;

import com.example.javalab2.dto.UsersDto.CreateUserDto;
import com.example.javalab2.exception.EmailAlreadyExistsException;
import com.example.javalab2.exception.NickNameAlreadyExistsException;
import com.example.javalab2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody CreateUserDto createUserDto) throws EmailAlreadyExistsException, NickNameAlreadyExistsException {
        userService.registerUser(createUserDto);
    }
}
