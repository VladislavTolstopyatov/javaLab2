package com.example.javalab2.services;

import com.example.javalab2.dto.UserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.exceptions.EmailAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.NickNameAlreadyExistsException;
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

    public UserDto saveUser(UserDto userDto) throws EmailAlreadyExistsException, NickNameAlreadyExistsException {
        User user = userRepository.findUserByEmail(userMapper.toEntity(userDto).getEmail());
        if (user != null) {
            throw new EmailAlreadyExistsException(String.format("User with email %s already exists",
                    userDto.getEmail()));
        }

        user = userRepository.findUserByNickName(userDto.getNickName());
        if (user != null) {
            throw new NickNameAlreadyExistsException(String.format("User with nickName %s already exists",
                    userDto.getNickName()));
        }

        user = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    public UserDto findUserById(Long id) throws ModelNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
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
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        userRepository.deleteById(id);
    }

    public UserDto findUserByNickName(String nickName) throws ModelNotFoundException {
        User user = userRepository.findUserByNickName(nickName);
        if (user == null) {
            throw new ModelNotFoundException(String.format("User with nickname %s not found", nickName));
        } else {
            return userMapper.toDto(user);
        }
    }

    public UserDto findUserByEmail(String email) throws ModelNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ModelNotFoundException(String.format("User with email %s not found", email));
        } else {
            return userMapper.toDto(user);
        }
    }

    public UserDto findUserByPasswordAndEmail(String password, String email) throws ModelNotFoundException {
        User user = userRepository.findUserByPasswordAndEmail(password, email);
        if (user == null) {
            throw new ModelNotFoundException(String.format("User with email %s and password not found", email));
        } else {
            return userMapper.toDto(user);
        }
    }

    public void deleteUserByNickName(String nickName) {
        userRepository.deleteUserByNickName(nickName);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }
}
