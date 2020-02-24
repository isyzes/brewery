package com.example.demo.converter;

import com.example.demo.dto.Ingredient;
import com.example.demo.dto.ItemRecipe;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.ItemRecipeEntity;

import java.util.ArrayList;
import java.util.List;

public class PartRecipeConverter {
    public static List<ItemRecipe> destinationToSource (List<ItemRecipeEntity> partRecipeEntities) {
        List<ItemRecipe> partRecipes = new ArrayList<ItemRecipe>(partRecipeEntities.size());
        for (ItemRecipeEntity part: partRecipeEntities) {
            ItemRecipe partRecipe = new ItemRecipe();
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
