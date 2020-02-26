package com.example.demo.dto;

import com.example.demo.dto.ingredient.Ingredient;
import lombok.Data;

@Data
public class RecipeItem {
    private long id;
    private Ingredient ingredient;
    private int milligram;
}
