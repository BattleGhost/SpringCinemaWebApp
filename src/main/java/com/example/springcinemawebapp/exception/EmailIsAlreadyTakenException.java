package com.example.springcinemawebapp.exception;

public class EmailIsAlreadyTakenException extends RuntimeException {

    public EmailIsAlreadyTakenException(String message) {
        super(message);
    }
}
