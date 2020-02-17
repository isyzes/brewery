package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.PartOrderEntity;
import com.example.demo.entity.PartRecipeEntity;
import com.example.demo.mapper.IngredientMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WarehouseService {
    private final IngredientRepository ingredientRepository;
    private final BeerRepository beerRepository;
    private final IngredientMapper ingredientMapper;


    public boolean thereIsIngredients (final BeerEntity beer) {
        for (PartRecipeEntity part: beer.getRecipe()) {

            final long id = part.getIngredientEntity().getId();

            final Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(id);

            if (ingredientEntity.isPresent()) {
                final IngredientEntity ingredient = ingredientEntity.get();

                if (ingredient.getMilligramsInStock() < part.getMilligram())
                    return false;

            } else return false;
        }

        return true;
    }

    public void buyIngredient(final Ingredient ingredient, final int needBuyMilligram) {
        final Optional<IngredientEntity> optionalIngredientEntity = ingredientRepository.findById(ingredient.getId());

        if (optionalIngredientEntity.isPresent()) {
            final IngredientEntity ingredientEntity = optionalIngredientEntity.get();

            ingredientEntity.setMilligramsInStock(ingredientEntity.getMilligramsInStock() + needBuyMilligram);

            ingredientRepository.save(ingredientEntity);
        } else {
            final IngredientEntity ingredientEntity = ingredientMapper.sourceToDestination(ingredient);
            ingredientEntity.setMilligramsInStock(ingredientEntity.getMilligramsInStock() + needBuyMilligram);
            ingredientRepository.save(ingredientEntity);
        }
    }

    public void takeIngredientsForBeer(final BeerEntity beerEntity) {
        for (PartRecipeEntity part: beerEntity.getRecipe()) {
            final long id = part.getIngredientEntity().getId();
            final Optional<IngredientEntity> optionalIngredientEntity = ingredientRepository.findById(id);

            final IngredientEntity ingredientEntity = optionalIngredientEntity.get();
            ingredientEntity.setMilligramsInStock(ingredientEntity.getMilligramsInStock() - part.getMilligram());
            ingredientRepository.save(ingredientEntity);
        }
    }



    public boolean thereIsBeer(final List<PartOrder> orders) {
        for (PartOrder part: orders) {
            final long id = part.getBeer().getId();

            final Optional<BeerEntity> optionalBeerEntity = beerRepository.findById(id);

            if (optionalBeerEntity.isPresent()) {
                final BeerEntity beerEntity = optionalBeerEntity.get();

                if (part.getQuantity() > beerEntity.getLitersInStock()) return false;

            } else return false;
        }

        return true;
    }

    public void sellBeer(final List<PartOrderEntity> orders) {
        for (PartOrderEntity part: orders) {
            final long id = part.getBeer().getId();
            final Optional<BeerEntity> optionalBeerEntity = beerRepository.findById(id);
            final BeerEntity beerEntity = optionalBeerEntity.get();

            beerEntity.setLitersInStock(beerEntity.getLitersInStock() - part.getQuantity());
            beerRepository.save(beerEntity);
        }
    }
}
