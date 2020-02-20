package com.example.demo.dto;

import lombok.Data;

@Data
public class PartRecipe {
    private long id;
    private Ingredient ingredient;
    private int milligram;
}
