package com.example.demo.controller;

import com.example.demo.dto.EmployeeSignUpRequest;
import com.example.demo.dto.UserSignInRequest;
import com.example.demo.dto.UserSignInResponse;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
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

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final EmployeeService employeeService;

    @PostMapping(value = "/employee/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignInResponse singUp(@RequestBody final EmployeeSignUpRequest request) throws SuchUserAlreadyExistException {
        employeeService.signUp(request);
        return singIn(new UserSignInRequest(request.getEmail(), request.getPassword()));
    }

    @PostMapping(value = "/employee/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserSignInResponse singIn(@RequestBody final UserSignInRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return new UserSignInResponse(
                jwtUtil.generateToken(
                        new User(request.getEmail(), request.getPassword(),
                                List.of(new SimpleGrantedAuthority("MANAGER")))));
    }
}
