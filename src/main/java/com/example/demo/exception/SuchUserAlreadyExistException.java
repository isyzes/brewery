package com.example.demo.exception;


public class SuchUserAlreadyExistException extends Exception {

    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }
}