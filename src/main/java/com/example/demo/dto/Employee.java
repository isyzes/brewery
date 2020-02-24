package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Employee {
    private long id;
    private String fio;
    private String department;
    private double wages;
    private  boolean isWorks;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateStart;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnd;
}
