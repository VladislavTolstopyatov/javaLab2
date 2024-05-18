package com.example.javalab2.mappers;

import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entities.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper extends Mappable<Actor, ActorDto> {
}
