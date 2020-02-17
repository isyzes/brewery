package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String department;
    private double wages;
    private  boolean isWorks;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateStart;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnd;
}
