package com.example.javalab2.mapper;

import com.example.javalab2.dto.UsersDto.UserDto;
import com.example.javalab2.entity.User;
import com.example.javalab2.entity.enums.Role;
import com.example.javalab2.repository.UserRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements Mappable<User, UserDto> {

    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto toDto(User userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickName(userEntity.getNickName())
                .feedbackList(feedBackMapper.toDto(userEntity.getFeedbacks()))
                .build();
    }

    @Override
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .nickName(dto.getNickName())
                .role(Role.USER)
                .feedbacks(feedBackMapper.toEntities(dto.getFeedbackList()))
                .password(userRepository.findUserByNickName(dto.getNickName()).getPassword())
                .build();
    }

    @Override
    public List<UserDto> toDto(List<User> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(this::toDto).toList();
    }

    @Override
    public List<User> toEntities(List<UserDto> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(this::toEntity).toList();
    }
}
