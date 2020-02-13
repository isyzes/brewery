package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class Warehouse {
    private List<Ingredient> ingredients;

    private List<FinishedBeer> finishedBeers;

}
