package com.example.javalab2.mappers;


import com.example.javalab2.dto.UsersDto.CreateUserDto;
import com.example.javalab2.entities.User;
import com.example.javalab2.entities.enums.Role;
import com.example.javalab2.mappers.Mappable;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CreateUserDtoMapper implements Mappable<User, CreateUserDto> {

    @Override
    public CreateUserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return CreateUserDto.builder().email(user.getEmail())
                .nickName(user.getNickName())
                .password(user.getPassword())
                .build();
    }

    @Override
    public User toEntity(CreateUserDto createUserDto) {
        if (createUserDto == null) {
            return null;
        }
        return User.builder().id(null)
                .email(createUserDto.getEmail())
                .nickName(createUserDto.getNickName())
                .password(createUserDto.getPassword())
                .role(Role.USER)
                .feedbacks(Collections.emptyList())
                .build();
    }

    @Override
    public List<CreateUserDto> toDto(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream().map(this::toDto).toList();
    }

    @Override
    public List<User> toEntities(List<CreateUserDto> createUserDtos) {
        if (createUserDtos == null) {
            return null;
        }
        return createUserDtos.stream().map(this::toEntity).toList();
    }
}
