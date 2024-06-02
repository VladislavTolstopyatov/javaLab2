package com.example.javalab2.services;

import com.example.javalab2.dto.UsersDto.CreateUserDto;
import com.example.javalab2.dto.UsersDto.UserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.exceptions.EmailAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.NickNameAlreadyExistsException;
import com.example.javalab2.mappers.CreateUserDtoMapper;
import com.example.javalab2.mappers.UserMapper;
import com.example.javalab2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"main"})
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CreateUserDtoMapper createUserDtoMapper;

    private final PasswordEncoder passwordEncoder;

    public CreateUserDto registerUser(CreateUserDto createUserDto) throws EmailAlreadyExistsException, NickNameAlreadyExistsException {
        User user = userRepository.findUserByEmail(createUserDtoMapper.toEntity(createUserDto).getEmail());
        if (user != null) {
            throw new EmailAlreadyExistsException(String.format("User with email %s already exists",
                    createUserDto.getEmail()));
        }

        user = userRepository.findUserByNickName(createUserDto.getNickName());
        if (user != null) {
            throw new NickNameAlreadyExistsException(String.format("User with nickName %s already exists",
                    createUserDto.getNickName()));
        }

        user = createUserDtoMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return createUserDtoMapper.toDto(user);
    }

    @Cacheable
    public List<UserDto> findAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Cacheable
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

    @Cacheable
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
