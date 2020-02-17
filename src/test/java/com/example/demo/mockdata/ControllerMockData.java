package com.example.demo.mockdata;

import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.PartRecipeEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ControllerMockData {
    public final static long ID = 3;


    public static Optional<BeerEntity> getNewOptionalBeer() {
        return Optional.of(getNewBeer());
    }

    public static Optional<BeerEntity> getNewOptionalBeer(final long id) {
        return Optional.of(getNewBeer(id));
    }

    public static Optional<IngredientEntity> getNewOptionalIngredient() {
        return Optional.of(getNewIngredient());
    }

    public static BeerEntity getNewBeer() {
        final BeerEntity beerEntity = new BeerEntity();
        beerEntity.setId(ID);
        beerEntity.setName("Grimbergen");
        beerEntity.setColor("bright");
        beerEntity.setFortress(12.5);
        beerEntity.setDateManufacture(LocalDate.of(2020, 12, 12));
        beerEntity.setShelfLife(25);
        beerEntity.setLitersInStock(6225);
        beerEntity.setRecipe(List.of(getNewPartRecipeEntity()));
        beerEntity.setCostPrice(657);
        return beerEntity;
    }
    public static BeerEntity getNewBeer(final long id) {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setId(id);
        return beerEntity;
    }

    public static PartRecipeEntity getNewPartRecipeEntity() {
        PartRecipeEntity partRecipeEntity = new PartRecipeEntity();
        partRecipeEntity.setId(ID);
        partRecipeEntity.setMilligram(5);
        partRecipeEntity.setIngredientEntity(getNewIngredient());
        return partRecipeEntity;
    }

    public static IngredientEntity getNewIngredient() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(ID);
        ingredientEntity.setName("Water");
        ingredientEntity.setMilligramsInStock(647851);
        return ingredientEntity;
    }

    public static List<BeerEntity> getBeerEntityList() {
        BeerEntity first = new BeerEntity();
        first.setId(1);
        first.setName("Garage");
        first.setColor("bright");
        first.setFortress(12.5);
        first.setDateManufacture(LocalDate.of(2020, 12, 12));
        first.setShelfLife(25);
        first.setCostPrice(574);

        BeerEntity second = new BeerEntity();
        second.setId(2);
        second.setName("Miller");
        second.setColor("bright");
        second.setFortress(12.5);
        second.setDateManufacture(LocalDate.of(2020, 12, 12));
        second.setShelfLife(25);
        second.setCostPrice(755);

        BeerEntity third = new BeerEntity();
        third.setId(3);
        third.setName("Heineken");
        third.setColor("dark");
        third.setFortress(9.5);
        third.setDateManufacture(LocalDate.of(2020, 12, 12));
        third.setShelfLife(35);
        third.setCostPrice(5756);
        System.out.println();

        return List.of(first, second, third);
    }

    public static OrderEntity getNewOrder() {
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setId(4);

//        PartRecipeEntity partRecipeEntity = new PartRecipeEntity();

//        PartRecipeEntity partRecipeEntity = new PartRecipeEntity();

//        orderEntity.setOrders();

        return new OrderEntity();
    }
}
