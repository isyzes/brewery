package com.example.demo.dto;

import lombok.Data;

@Data
public class UserSignInRequest {
    private final String email;
    private final String password;
}
