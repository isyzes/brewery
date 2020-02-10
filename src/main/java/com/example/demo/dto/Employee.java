package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private long id;
    private String name;
    private String department;
    private double wages;
    private  boolean isWorks;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateStart;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnd;

    public Employee(long id, String name, String department, double wages, boolean isWorks, LocalDate dateStart, LocalDate dateEnd) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.wages = wages;
        this.isWorks = isWorks;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
