package com.example.demo.service;

import com.example.demo.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerService {


    public void setNeedBeer(boolean needBeer) {

    }

    public void setNeedIngredient(boolean needIngredient) {

    }

    //   private final IngredientService warehouseService;

//    public void needIngredientForBeer(Beer beer) {
//        for (PartRecipe part : beer.getRecipe()) {
//            final Ingredient ingredient = part.getIngredient();
//            final int needBuyMilligram = ingredient.getMilligramsInStock() - part.getMilligram();
//
//            buyIngredient(ingredient, needBuyMilligram);
//        }
//    }

//    private void buyIngredient(final Ingredient ingredient, final int needBuyMilligram) {
//        warehouseService.buyIngredient(ingredient, needBuyMilligram);
//   }

}
