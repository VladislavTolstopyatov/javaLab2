package com.example.javalab2.mapper;

import java.util.List;

public interface Mappable<E, D> {
    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDto(List<E> entities);

    List<E> toEntities(List<D> dto);
}
