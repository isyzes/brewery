package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.PartOrderEntity;
import com.example.demo.entity.PartRecipeEntity;
import com.example.demo.exception.BuyIngredientException;
import com.example.demo.exception.SuchUserAlreadyExistException;
import com.example.demo.mapper.IngredientMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public void buyIngredient(final IngredientRequestOrder ingredientRequestOrder) throws BuyIngredientException {
        final long ingredientId = ingredientRequestOrder.getIdIngredient();
        final int needBuyMilligram = ingredientRequestOrder.getMilligramsInStock();
        final Optional<IngredientEntity> optionalIngredientEntity = ingredientRepository.findById(ingredientId);

        if (optionalIngredientEntity.isPresent()) {
            final IngredientEntity ingredientEntity = optionalIngredientEntity.get();
            final int totalMilligramsInStock = ingredientEntity.getMilligramsInStock() + needBuyMilligram;

            ingredientEntity.setMilligramsInStock(totalMilligramsInStock);
            ingredientRepository.save(ingredientEntity);
        } else {
            throw new BuyIngredientException("Ingredient with id=" + ingredientId + " not found!");
        }
    }

    public boolean thereIsIngredients (final List<PartRecipe> recipe) {
        for (PartRecipe part: recipe) {
            final long id = part.getIngredient().getId();

            //TODO: remove database access in a loop
            final Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(id);

            if (ingredientEntity.isPresent()) {
                final IngredientEntity ingredient = ingredientEntity.get();

                if (ingredient.getMilligramsInStock() < part.getMilligram())
                    return false;

            } else return false;
        }
        return true;
    }

    public void takeIngredientsForBeer(List<PartRecipe> recipe) {
        for (PartRecipe part: recipe) {
            final long id = part.getIngredient().getId();
            //TODO: remove database access in a loop
            final Optional<IngredientEntity> optionalIngredientEntity = ingredientRepository.findById(id);

            final IngredientEntity ingredientEntity = optionalIngredientEntity.get();
            final int totalMilligramsInStock = ingredientEntity.getMilligramsInStock() - part.getMilligram();
            ingredientEntity.setMilligramsInStock(totalMilligramsInStock);
            ingredientRepository.save(ingredientEntity);
        }
    }
}
