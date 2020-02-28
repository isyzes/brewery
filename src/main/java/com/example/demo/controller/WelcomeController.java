package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/index")
    public String  welcome() {
       return "Welcome!";
    }
}
