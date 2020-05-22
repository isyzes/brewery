package com.example.demo.controller;

import com.example.demo.dto.authentication.UserSignInRequest;
import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.dto.authentication.UserSignUpRequest;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignInResponse signUp(@RequestBody final UserSignUpRequest request) throws SuchUserAlreadyExistException {
        return authService.signUp(request);
    }

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserSignInResponse signIn(@RequestBody final UserSignInRequest request) {
        return authService.signIn(request);
    }
}
