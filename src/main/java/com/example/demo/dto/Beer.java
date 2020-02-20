package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Beer {
    private long id;
    private String name;
    private String color;
    private double fortress;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateManufacture;
    private int shelfLife;//(дней)
    private int costPrice;//в копейках
    private List<PartRecipe> recipe;
}

