package com.example.demo.controller;

import com.example.demo.exception.*;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;

@Log
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({BreweryIngredientException.class, BreweryBeerException.class, SuchUserAlreadyExistException.class,
            UsernameNotFoundException.class, BreweryUpdatedBeerException.class})
    private ResponseEntity<ErrorMessage> handleBadRequest(final Exception e) {
        log.log(Level.SEVERE, e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SuchUserNotFoundException.class})
    private ResponseEntity<ErrorMessage> handleForbidden(final Exception e) {
        log.log(Level.SEVERE, e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @Data
    public static class ErrorMessage {
        private final String errorMessage;
    }
}
