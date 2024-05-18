package com.example.javalab2.exceptions;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
