package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    MANAGER,
    EMPLOYEE,
    CONSUMER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
