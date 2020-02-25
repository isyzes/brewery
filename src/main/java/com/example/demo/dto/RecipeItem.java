package com.example.demo.dto;

import lombok.Data;

@Data
public class RecipeItem {
    private long id;
    private Ingredient ingredient;
    private int milligram;
}
