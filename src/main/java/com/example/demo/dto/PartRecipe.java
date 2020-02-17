package com.example.demo.dto;

import com.example.demo.entity.IngredientEntity;
import lombok.Data;

@Data
public class PartRecipe {
    private long id;
    private Ingredient ingredient;
    private int milligram;
}
