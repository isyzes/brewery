package com.example.demo.service;

import com.example.demo.dto.recipe.RecipeItem;
import com.example.demo.dto.ingredient.IngredientRequestOrder;
import com.example.demo.dto.ingredient.IngredientResponseOrder;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.exception.BreweryIngredientException;
import com.example.demo.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Transactional
    public IngredientResponseOrder buyIngredient(final IngredientRequestOrder ingredientRequestOrder) throws BreweryIngredientException {
        final long ingredientId = ingredientRequestOrder.getIdIngredient();
        final int needBuyMilligram = ingredientRequestOrder.getMilligramsInStock();
        final Optional<IngredientEntity> optionalIngredientEntity = ingredientRepository.findById(ingredientId);

        if (optionalIngredientEntity.isPresent()) {
            final IngredientEntity ingredientEntity = optionalIngredientEntity.get();
            final int totalMilligramsInStock = ingredientEntity.getMilligramsInStock() + needBuyMilligram;

            final IngredientResponseOrder response = new IngredientResponseOrder();
            response.setIdIngredient(ingredientId);
            response.setTotalMilligramsInStock(totalMilligramsInStock);

            ingredientEntity.setMilligramsInStock(totalMilligramsInStock);
            ingredientRepository.save(ingredientEntity);

            return response;
        } else {
            throw new BreweryIngredientException("Ingredient with id=" + ingredientId + " not found!");
        }
    }

    public List<IngredientEntity> ingredientsOfRecipe(final List<RecipeItem> recipe, final int liters) {
        final List<IngredientEntity> result = new ArrayList<>();

        final AtomicInteger sum = new AtomicInteger();

        recipe.stream()
                .peek(part ->sum.set(part.getMilligram() * liters))
                .map(part -> ingredientRepository.findById(part.getIngredient().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(ingredient -> !(ingredient.getMilligramsInStock() < (sum.get())))
                .forEach(result::add);

        return result;
    }

    public void takeIngredientsForBeer(final List<RecipeItem> recipe, final List<IngredientEntity> ingredientEntities) {
        for (RecipeItem part: recipe) {
            final long id = part.getIngredient().getId();
            final IngredientEntity ingredientEntity = getIngredient(id, ingredientEntities);
            final int totalMilligramsInStock = ingredientEntity.getMilligramsInStock() - part.getMilligram();
            ingredientEntity.setMilligramsInStock(totalMilligramsInStock);
            ingredientRepository.save(ingredientEntity);
        }
    }

    private IngredientEntity getIngredient(final long id, final List<IngredientEntity> ingredientEntities) {
        return ingredientEntities.stream()
                .filter(ingredient -> ingredient.getId() == id)
                .findFirst()
                .get();
    }
}
