package com.example.demo.service;

import com.example.demo.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerService {
   private final WarehouseService warehouseService;

    public void needIngredientForBeer(Beer beer) {
        for (PartRecipe part : beer.getRecipe()) {
            final Ingredient ingredient = part.getIngredient();
            final int needBuyMilligram = ingredient.getMilligramsInStock() - part.getMilligram();

            buyIngredient(ingredient, needBuyMilligram);
        }
    }

    private void buyIngredient(final Ingredient ingredient, final int needBuyMilligram) {
        warehouseService.buyIngredient(ingredient, needBuyMilligram);
   }

}
