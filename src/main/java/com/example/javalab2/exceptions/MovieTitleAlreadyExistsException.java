package com.example.javalab2.exceptions;

public class MovieTitleAlreadyExistsException extends Throwable {
    public MovieTitleAlreadyExistsException(String message) {
        super(message);
    }
}

