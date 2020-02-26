package com.example.demo.controller;

import com.example.demo.dto.authentication.UserSignUpRequest;
import com.example.demo.dto.authentication.UserSignInRequest;
import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping(value = "/employee/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignInResponse singUp(@RequestBody final UserSignUpRequest request) throws SuchUserAlreadyExistException {
        return userService.signUp(request);
    }

    @PostMapping(value = "/employee/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserSignInResponse singIn(@RequestBody final UserSignInRequest request) {
        return userService.singIn(request);
    }
}
