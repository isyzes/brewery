package com.example.demo.mockdata;

import com.example.demo.controller.AbstractControllerTest;
import com.example.demo.entity.*;
import com.example.demo.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ControllerMockData {
    public final static long ID = 3;
//    @Autowired
//    protected static PasswordEncoder passwordEncoder;


    public static Optional<BeerEntity> getNewOptionalBeer() {
        return Optional.of(getNewBeer());
    }

    public static Optional<BeerEntity> getNewOptionalBeer(final long id) {
        return Optional.of(getNewBeer(id));
    }

    public static Optional<IngredientEntity> getNewOptionalIngredient() {
        return Optional.of(getNewIngredient(647851));
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
        beerEntity.setRecipe(getRecipeEntity());
        beerEntity.setCostPrice(657);
        return beerEntity;
    }
    public static BeerEntity getNewBeer(final long id) {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setId(id);
        return beerEntity;
    }

    public static RecipeEntity getRecipeEntity() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(ID);
        recipeEntity.setItems(List.of(getNewPartRecipeEntity()));
        return recipeEntity;
    }

    public static RecipeItemEntity getNewPartRecipeEntity() {
        final RecipeItemEntity partRecipeEntity = new RecipeItemEntity();
        partRecipeEntity.setId(ID);
        partRecipeEntity.setMilligram(5);
        partRecipeEntity.setIngredientEntity(getNewIngredient(647851));
        return partRecipeEntity;
    }

    public static IngredientEntity getNewIngredient(final int milligramsInStock) {
        final IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(ID);
        ingredientEntity.setName("Water");
        ingredientEntity.setMilligramsInStock(milligramsInStock);
        return ingredientEntity;
    }

    public static List<BeerEntity> getBeerEntityList() {
        final BeerEntity first = new BeerEntity();
        first.setId(1);
        first.setName("Garage");
        first.setColor("bright");
        first.setFortress(12.5);
        first.setDateManufacture(LocalDate.of(2020, 12, 12));
        first.setShelfLife(25);
        first.setCostPrice(574);

        final BeerEntity second = new BeerEntity();
        second.setId(2);
        second.setName("Miller");
        second.setColor("bright");
        second.setFortress(12.5);
        second.setDateManufacture(LocalDate.of(2020, 12, 12));
        second.setShelfLife(25);
        second.setCostPrice(755);

        final BeerEntity third = new BeerEntity();
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

    public static UserEntity getNewEmployeeEntity(final long id) {
        final UserEntity employeeEntity = new UserEntity();
        employeeEntity.setUserRole(Roles.EMPLOYEE);
        employeeEntity.setId(id);
        return employeeEntity;
    }

    public static Optional<UserEntity> getOptionalEmployeeEntity() {
        return Optional.of(getNewEmployeeEntity());
    }

    public static UserEntity getNewEmployeeEntity() {
        final UserEntity employeeEntity = new UserEntity();
        employeeEntity.setId(ID);
        employeeEntity.setUserRole(Roles.EMPLOYEE);
        employeeEntity.setWorks(true);

        return employeeEntity;
    }

    public static List<UserEntity> getEmployeeEntities() {
        final UserEntity firstEmployee = new UserEntity();
        firstEmployee.setId(5L);
        firstEmployee.setFio("Adam Gordon");
        firstEmployee.setDepartment("Production");
        firstEmployee.setWages(2500);
        firstEmployee.setWorks(true);
        firstEmployee.setDateStart(LocalDate.of(2018, 1,15));
        firstEmployee.setDateEnd(null);
        firstEmployee.setUserRole(Roles.EMPLOYEE);

        final UserEntity secondEmployee = new UserEntity();
        secondEmployee.setId(2L);
        secondEmployee.setFio("Carla Williams");
        secondEmployee.setDepartment("Production");
        secondEmployee.setWages(5070);
        secondEmployee.setWorks(true);
        secondEmployee.setDateStart(LocalDate.of(2018, 1,15));
        secondEmployee.setDateEnd(null);
        secondEmployee.setUserRole(Roles.EMPLOYEE);

        final UserEntity thirdEmployee = new UserEntity();
        thirdEmployee.setId(4L);
        thirdEmployee.setFio("Boris Jones");
        thirdEmployee.setDepartment("Production");
        thirdEmployee.setWages(1500);
        thirdEmployee.setWorks(false);
        thirdEmployee.setDateStart(LocalDate.of(2018, 1,15));
        thirdEmployee.setDateEnd(LocalDate.of(2019,10,14));
        thirdEmployee.setUserRole(Roles.EMPLOYEE);

        return List.of(firstEmployee, secondEmployee, thirdEmployee);
    }

    public static OrderEntity getNewOrderEntity() {
        final OrderEntity orderEntity = new OrderEntity();

        orderEntity.setPrice(26.28);
        orderEntity.setItem(getNewOrderItemEntity());
        orderEntity.setConsumer(getNewConsumerEntity());

        return orderEntity;
    }

    public static UserEntity getNewConsumerEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(4L);
        userEntity.setFio("Easy Pub");
        userEntity.setUserRole(Roles.CONSUMER);
        return userEntity;
    }

    public static List<OrderItemEntity> getNewOrderItemEntity() {
        final OrderItemEntity firstOrderItemEntity = new OrderItemEntity();
        firstOrderItemEntity.setBeer(getNewBeer(3));
        firstOrderItemEntity.setLiters(5);

        final OrderItemEntity secondOrderItemEntity = new OrderItemEntity();
        secondOrderItemEntity.setBeer(getNewBeer(4));
        secondOrderItemEntity.setLiters(4);

        return List.of(firstOrderItemEntity, secondOrderItemEntity);

    }

    public static AuthInfoEntity getNewAuthInfoEntity() {
        final AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin("vasya@email.com");
//        authInfoEntity.setPassword();
        authInfoEntity.setUser(getAuthNewConsumerEntity());
        return authInfoEntity;
    }

    public static UserEntity getAuthNewConsumerEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("vasya@email.com");
        userEntity.setBirthDate(LocalDate.of(1995, 1, 19));
        userEntity.setFio("Пупкин Василий Иванович");
        userEntity.setUserRole(Roles.CONSUMER);
        return userEntity;
    }



}
