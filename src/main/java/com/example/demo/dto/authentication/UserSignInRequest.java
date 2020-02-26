package com.example.demo.dto.authentication;

import lombok.Data;

@Data
public class UserSignInRequest {
    private final String email;
    private final String password;
}
