package com.example.demo.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {
    private long id;
    private List<RecipeItem> items;
}
