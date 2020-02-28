package com.example.demo.mockdata;

import com.example.demo.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.demo.security.Roles.CONSUMER;
import static com.example.demo.security.Roles.EMPLOYEE;

public class ControllerMockData {
    private final static long ID = 3;

    public static Optional<BeerEntity> getNewOptionalBeer() {
        return Optional.of(getNewBeer());
    }

    public static Optional<BeerEntity> getNewOptionalBeer(final long id) {
        return Optional.of(getNewBeer(id));
    }

    public static Optional<UserEntity> getOptionalEmployeeEntity() {
        return Optional.of(getNewEmployeeEntity());
    }

    public static Optional<IngredientEntity> getNewOptionalIngredient() {
        return Optional.of(getNewIngredient(647851));
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

    public static List<UserEntity> getEmployeeEntities() {
        final UserEntity firstEmployee = new UserEntity();
        firstEmployee.setId(5L);
        firstEmployee.setFio("Adam Gordon");
        firstEmployee.setDepartment("Production");
        firstEmployee.setWages(2500);
        firstEmployee.setWorks(true);
        firstEmployee.setDateStart(LocalDate.of(2018, 1,15));
        firstEmployee.setDateEnd(null);
        firstEmployee.setUserRole(EMPLOYEE);

        final UserEntity secondEmployee = new UserEntity();
        secondEmployee.setId(2L);
        secondEmployee.setFio("Carla Williams");
        secondEmployee.setDepartment("Production");
        secondEmployee.setWages(5070);
        secondEmployee.setWorks(true);
        secondEmployee.setDateStart(LocalDate.of(2018, 1,15));
        secondEmployee.setDateEnd(null);
        secondEmployee.setUserRole(EMPLOYEE);

        final UserEntity thirdEmployee = new UserEntity();
        thirdEmployee.setId(4L);
        thirdEmployee.setFio("Boris Jones");
        thirdEmployee.setDepartment("Production");
        thirdEmployee.setWages(1500);
        thirdEmployee.setWorks(false);
        thirdEmployee.setDateStart(LocalDate.of(2018, 1,15));
        thirdEmployee.setDateEnd(LocalDate.of(2019,10,14));
        thirdEmployee.setUserRole(EMPLOYEE);

        return List.of(firstEmployee, secondEmployee, thirdEmployee);
    }

    public static OrderEntity getNewOrderEntity() {
        final OrderEntity orderEntity = new OrderEntity();

        orderEntity.setPrice(26.28);
        orderEntity.setItems(getNewListOrderItemEntity());
        orderEntity.setConsumer(getNewConsumerEntity());

        return orderEntity;
    }

    public static OrderItemEntity getNewOrderItemEntity(long idBeer, int liters) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
//        orderItemEntity.setOrder(getNewOrderEntity());
        orderItemEntity.setLiters(liters);
        orderItemEntity.setBeer(getNewBeer(idBeer));

        return orderItemEntity;
    }

    public static UserEntity getAuthNewConsumerEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("vasya@email.com");
        userEntity.setBirthDate(LocalDate.of(1995, 1, 19));
        userEntity.setFio("Пупкин Василий Иванович");
        userEntity.setUserRole(CONSUMER);
        return userEntity;
    }

    public static UserEntity getNewEmployeeRequestEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFio("Ivanov Ivan Ivanovich");
        userEntity.setDepartment("Production");
        userEntity.setWages(2020);

        return userEntity;
    }

    public static UserEntity getNewEmployeeResponseEntity() {
        UserEntity userEntity = getNewEmployeeRequestEntity();
        userEntity.setId(0L);
        userEntity.setUserRole(EMPLOYEE);
        userEntity.setWorks(true);
        userEntity.setDateStart(LocalDate.now());

        return userEntity;
    }

    public static UserEntity getAuthNewEmployeeEntity() {
        UserEntity userEntity = getAuthNewConsumerEntity();
        userEntity.setUserRole(EMPLOYEE);
        userEntity.setId(ID);
        userEntity.setWorks(true);
        return userEntity;
    }

    public static UserEntity getAuthNewEmployeeToDismiss() {
        UserEntity userEntity = getNewEmployeeEntity();
        userEntity.setWorks(false);
        userEntity.setDateEnd(LocalDate.now());

        return userEntity;
    }

    public static BeerEntity getResponseUpdatedBeer() {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setName("Grimbergen");
        beerEntity.setCostPrice(2551);
        return beerEntity;
    }

    public static BeerEntity getSaveBeer() {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setLitersInStock(beerEntity.getLitersInStock() + 2551);
        return beerEntity;
    }


    private static UserEntity getNewConsumerEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(4L);
        userEntity.setFio("Easy Pub");
        userEntity.setUserRole(CONSUMER);
        return userEntity;
    }

    private static UserEntity getNewEmployeeEntity() {
        final UserEntity employeeEntity = new UserEntity();
        employeeEntity.setId(ID);
        employeeEntity.setUserRole(EMPLOYEE);
        employeeEntity.setWorks(true);

        return employeeEntity;
    }

    private static BeerEntity getNewBeer() {
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

    private static BeerEntity getNewBeer(final long id) {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setId(id);
        return beerEntity;
    }

    private static RecipeEntity getRecipeEntity() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(ID);
        recipeEntity.setItems(List.of(getNewPartRecipeEntity()));
        return recipeEntity;
    }

    private static RecipeItemEntity getNewPartRecipeEntity() {
        final RecipeItemEntity partRecipeEntity = new RecipeItemEntity();
        partRecipeEntity.setId(ID);
        partRecipeEntity.setMilligram(5);
        partRecipeEntity.setIngredient(getNewIngredient(647851));
        return partRecipeEntity;
    }

    private static List<OrderItemEntity> getNewListOrderItemEntity() {
        final OrderItemEntity firstOrderItemEntity = new OrderItemEntity();
        firstOrderItemEntity.setBeer(getNewBeer(3));
        firstOrderItemEntity.setLiters(5);

        final OrderItemEntity secondOrderItemEntity = new OrderItemEntity();
        secondOrderItemEntity.setBeer(getNewBeer(4));
        secondOrderItemEntity.setLiters(4);

        return List.of(firstOrderItemEntity, secondOrderItemEntity);

    }
}
