package com.example.demo.dto.ingredient;

import lombok.Data;

@Data
public class IngredientResponseOrder {
    private long idIngredient;
    private int totalMilligramsInStock;
}
