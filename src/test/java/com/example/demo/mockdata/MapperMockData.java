package com.example.demo.mockdata;

import com.example.demo.entity.*;

import java.time.LocalDate;
import java.util.List;

public class MapperMockData {
    private final static long ID = 3;

    protected static BeerEntity getBeerEntity() {
        final BeerEntity beerEntity = new BeerEntity();
        beerEntity.setId(ID);
        beerEntity.setName("Grimbergen");
        beerEntity.setColor("bright");
        beerEntity.setFortress(12.5);
        beerEntity.setDateManufacture(LocalDate.of(2020, 12, 12));
        beerEntity.setShelfLife(25);
        beerEntity.setLitersInStock(6225);
        beerEntity.setRecipe(getRecipeEntity());
        beerEntity.setCostPrice(657);
        beerEntity.setLitersInStock(2551);
        return beerEntity;
    }

    protected static UserEntity getUserEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(ID);
        userEntity.setDateStart(LocalDate.of(2018, 1,15));
        userEntity.setDateEnd(LocalDate.of(2019,10,14));
        userEntity.setEmail("vasya@email.com");
        userEntity.setBirthDate(LocalDate.of(1995, 1, 19));
        userEntity.setFio("Пупкин Василий Иванович");
        return userEntity;
    }

    protected static IngredientEntity getIngredientEntity() {
        final IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(ID);
        ingredientEntity.setName("Water");
        ingredientEntity.setMilligramsInStock(647851);
        return ingredientEntity;
    }

    protected static List<BeerEntity> getBeerEntityList() {
        final BeerEntity first = new BeerEntity();
        first.setId(1);
        first.setName("Garage");
        first.setColor("bright");
        first.setFortress(12.5);
        first.setDateManufacture(LocalDate.of(2020, 12, 12));
        first.setShelfLife(25);
        first.setCostPrice(574);
        first.setRecipe(getRecipeEntity());

        final BeerEntity second = new BeerEntity();
        second.setId(2);
        second.setName("Miller");
        second.setColor("bright");
        second.setFortress(12.5);
        second.setDateManufacture(LocalDate.of(2020, 12, 12));
        second.setShelfLife(25);
        second.setCostPrice(755);
        second.setRecipe(getRecipeEntity());

        final BeerEntity third = new BeerEntity();
        third.setId(3);
        third.setName("Heineken");
        third.setColor("dark");
        third.setFortress(9.5);
        third.setDateManufacture(LocalDate.of(2020, 12, 12));
        third.setShelfLife(35);
        third.setCostPrice(5756);
        third.setRecipe(getRecipeEntity());

        return List.of(first, second, third);
    }

    protected static List<RecipeItemEntity> getRecipeItemEntityList() {
        return List.of(getRecipeItemEntity());
    }

    protected static OrderItemEntity getOrderItemEntity() {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setLiters(6445);
        orderItemEntity.setBeer(getBeerEntity());

        return orderItemEntity;
    }

    protected static OrderEntity getOrderEntity() {
        return getOrderEntity(getOrderItemEntity(), getOrderItemEntity());
    }

    protected static RecipeEntity getRecipeEntity() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(ID);
        recipeEntity.setItems(List.of(getRecipeItemEntity()));
        return recipeEntity;
    }

    private static RecipeItemEntity getRecipeItemEntity() {
        final RecipeItemEntity partRecipeEntity = new RecipeItemEntity();
        partRecipeEntity.setId(ID);
        partRecipeEntity.setMilligram(5);
        partRecipeEntity.setIngredient(getIngredientEntity());
        return partRecipeEntity;
    }

    private static OrderEntity getOrderEntity(final OrderItemEntity firstSaveItem, final OrderItemEntity secondSaveItem) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPrice(26.28);
        orderEntity.setItems(List.of(firstSaveItem, secondSaveItem));
        orderEntity.setConsumer(getUserEntity());

        return orderEntity;
    }
}
