package com.example.javalab2.mapper;

import com.example.javalab2.dto.DirectorDto;
import com.example.javalab2.entity.Director;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public  abstract class DirectorMapper implements Mappable<Director, DirectorDto> {

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public DirectorDto toDto(Director directorEntity) {
        if (directorEntity == null) {
            return null;
        }
        return DirectorDto.builder()
                .id(directorEntity.getId())
                .name(directorEntity.getName())
                .surname(directorEntity.getSurname())
                .patronymic(directorEntity.getPatronymic())
                .birthdate(directorEntity.getBirthdate())
                .oscar(directorEntity.getOscar())
                .movieDtoList(movieMapper.toDto(directorEntity.getMovies()))
                .build();
    }

    @Override
    public Director toEntity(DirectorDto dto) {
        if (dto == null) {
            return null;
        }
        return Director.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .patronymic(dto.getPatronymic())
                .birthdate(dto.getBirthdate())
                .oscar(dto.getOscar())
                .movies(movieMapper.toEntities(dto.getMovieDtoList()))
                .build();
    }

    @Override
    public List<DirectorDto> toDto(List<Director> directorEntities) {
        if (directorEntities == null) {
            return null;
        }
        return directorEntities.stream().map(this::toDto).toList();
    }

    @Override
    public List<Director> toEntities(List<DirectorDto> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(this::toEntity).toList();
    }
}
