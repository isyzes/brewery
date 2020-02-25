package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {
    private long id;
    private List<RecipeItem> recipe;
}
