package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Manager {
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
