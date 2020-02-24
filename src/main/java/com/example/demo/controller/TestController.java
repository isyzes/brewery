package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/test")
    public String  test() {
       return "Hello World";
    }
}
