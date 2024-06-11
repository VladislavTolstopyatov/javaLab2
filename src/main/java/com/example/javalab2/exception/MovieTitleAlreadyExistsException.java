package com.example.javalab2.exception;

public class MovieTitleAlreadyExistsException extends Throwable {
    public MovieTitleAlreadyExistsException(String message) {
        super(message);
    }
}

