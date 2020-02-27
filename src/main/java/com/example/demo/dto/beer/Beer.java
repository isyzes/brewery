package com.example.demo.dto.beer;

import com.example.demo.dto.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Beer {
    private long id;
    private String name;
    private String color;
    private double fortress;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateManufacture;
    private int shelfLife;
    private int costPrice;
    private Recipe recipe;
}

