package com.example.demo.converter;

import com.example.demo.dto.Ingredient;
import com.example.demo.dto.PartRecipe;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.PartRecipeEntity;

import java.util.ArrayList;
import java.util.List;

public class PartRecipeConverter {
    public static List<PartRecipe> destinationToSource (List<PartRecipeEntity> partRecipeEntities) {
        List<PartRecipe> partRecipes = new ArrayList<PartRecipe>(partRecipeEntities.size());
        for (PartRecipeEntity part: partRecipeEntities) {
            PartRecipe partRecipe = new PartRecipe();
            partRecipe.setId(part.getId());
            partRecipe.setMilligram(part.getMilligram());
            partRecipe.setIngredient(destinationToSource(part.getIngredientEntity()));

            partRecipes.add(partRecipe);
        }

        return partRecipes;
    }

    private static Ingredient destinationToSource(IngredientEntity ingredientEntity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientEntity.getId());
        ingredient.setName(ingredientEntity.getName());
        ingredient.setMilligramsInStock(ingredientEntity.getMilligramsInStock());

        return ingredient;
    }
}
