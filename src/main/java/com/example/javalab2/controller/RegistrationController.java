package com.example.javalab2.controller;

import com.example.javalab2.dto.UsersDto.CreateUserDto;
import com.example.javalab2.exception.EmailAlreadyExistsException;
import com.example.javalab2.exception.NickNameAlreadyExistsException;
import com.example.javalab2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Registration Controller",description = "Обрабатывает запрос, связанный с регистрацией нового пользователя")
@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @Operation(summary = "Зарегистрироваться")
    @PostMapping("/register")
    public void register(@RequestBody CreateUserDto createUserDto) throws EmailAlreadyExistsException, NickNameAlreadyExistsException {
        userService.registerUser(createUserDto);
    }
}
