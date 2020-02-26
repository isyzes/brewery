package com.example.demo.dto.ingredient;

import lombok.Data;

@Data
public class IngredientRequestOrder {
    private long idIngredient;
    private int milligramsInStock;
}
