package com.example.demo.dto.ingredient;

import lombok.Data;

@Data
public class Ingredient {
    private long id;
    private String name;
    private int milligramsInStock;
}
