package com.example.javalab2.mapper;

import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper extends Mappable<Actor, ActorDto> {
}
