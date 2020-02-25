package com.example.demo.exception;


public class SuchUserAlreadyExistException extends Exception {

    public SuchUserAlreadyExistException() {
        super();
    }

    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }
}//https://github.com/wgdetective/java-training-example/blob/origin/feature/lesson_5/src/main/java/com/gpsolutions/edu/java/training/example/controller/ExceptionControllerAdvice.java