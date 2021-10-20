package com.example.springcinemawebapp.exception;

public class TimeOverlappingException extends RuntimeException {
    public TimeOverlappingException(String message) {
        super(message);
    }
}
