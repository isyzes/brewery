package com.example.demo.entity;

import com.example.demo.security.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "fio")
    private String fio;
    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    @Column(name = "self_description")
    private String selfDescription;
    @Column(name = "user_role")
    private Roles userRole;
    @Column(name = "department")
    private String department;
    @Column(name = "wages")
    private double wages;
    @Column(name = "is_works")
    private  boolean isWorks;
    @Column(name = "date_start")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateStart;
    @Column(name = "date_end")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnd;
}
