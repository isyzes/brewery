package com.example.demo.converter;

import com.example.demo.dto.ingredient.Ingredient;
import com.example.demo.dto.RecipeItem;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.RecipeItemEntity;

import java.util.ArrayList;
import java.util.List;

public class PartRecipeConverter {
    public static List<RecipeItem> destinationToSource (List<RecipeItemEntity> partRecipeEntities) {
        List<RecipeItem> partRecipes = new ArrayList<RecipeItem>(partRecipeEntities.size());
        for (RecipeItemEntity part: partRecipeEntities) {
            RecipeItem partRecipe = new RecipeItem();
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
