package com.example.javalab2.entities.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Genre {
    DRAMA,
    COMEDY,
    DETECTIVE,
    FANTASY,
    MELODRAMA;

    public static Optional<Genre> find(final String genre) {
        return Arrays.stream(Genre.values())
                .filter(gen -> gen.name().equals(genre))
                .findFirst();
    }
}
