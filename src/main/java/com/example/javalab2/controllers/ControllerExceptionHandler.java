package com.example.javalab2.controllers;

import com.example.javalab2.exceptions.ActorFioAlreadyExistsException;
import com.example.javalab2.exceptions.EmailAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.exceptions.MovieTitleAlreadyExistsException;
import com.example.javalab2.exceptions.NickNameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleModelNotFoundException(ModelNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MovieTitleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMovieTitleAlreadyExistsException(MovieTitleAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ActorFioAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleActorFioAlreadyExistsException(ActorFioAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NickNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNickNameAlreadyExistsException(NickNameAlreadyExistsException e) {
        return e.getMessage();
    }
}
