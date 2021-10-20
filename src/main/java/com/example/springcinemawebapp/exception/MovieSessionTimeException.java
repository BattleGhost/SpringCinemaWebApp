package com.example.springcinemawebapp.exception;

public class MovieSessionTimeException extends RuntimeException {
    public MovieSessionTimeException(String message) {
        super(message);
    }
}
