package com.example.javalab2.services;

import com.example.javalab2.dto.UserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.mappers.UserMapper;
import com.example.javalab2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto saveUser(UserDto userDto) {
        User user = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    public UserDto findUserById(Long id) throws ModelNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return userMapper.toDto(optionalUser.get());
        } else {
            throw new ModelNotFoundException(String.format("User with id %d not found", id));
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteUserById(Long id) {

    }


}
