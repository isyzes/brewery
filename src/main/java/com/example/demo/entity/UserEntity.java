package com.example.demo.entity;

import com.example.demo.security.Roles;
import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String fio;
    private LocalDate birthDate;
    private String selfDescription;

    private Roles userRole;
}
