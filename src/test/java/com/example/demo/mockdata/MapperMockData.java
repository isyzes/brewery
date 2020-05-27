package com.example.demo.mockdata;

import com.example.demo.entity.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.security.Roles.EMPLOYEE;

public class MapperMockData {
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

        final UserEntity secondEmployee = new UserEntity();
        secondEmployee.setId(2L);
        secondEmployee.setFio("Carla Williams");
        secondEmployee.setDepartment("Production");
        secondEmployee.setWages(5070);
        secondEmployee.setWorks(true);
        secondEmployee.setDateStart(LocalDate.of(2018, 1,15));
        secondEmployee.setDateEnd(null);

        final UserEntity thirdEmployee = new UserEntity();
        thirdEmployee.setId(4L);
        thirdEmployee.setFio("Boris Jones");
        thirdEmployee.setDepartment("Production");
        thirdEmployee.setWages(1500);
        thirdEmployee.setWorks(false);
        thirdEmployee.setDateStart(LocalDate.of(2018, 1,15));
        thirdEmployee.setDateEnd(LocalDate.of(2019,10,14));

        return List.of(firstEmployee, secondEmployee, thirdEmployee);
    }

    public static List<BeerEntity> getBeerEntityListForConverter() {
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

    public static List<AuthInfoEntity> getAuthInfoEntity() {
        final UserEntity firstEmployee = new UserEntity();
        firstEmployee.setId(5L);
        firstEmployee.setFio("Adam Gordon");
        firstEmployee.setDepartment("Production");
        firstEmployee.setWages(2500);
        firstEmployee.setWorks(true);
        firstEmployee.setDateStart(LocalDate.of(2018, 1,15));
        firstEmployee.setDateEnd(null);

        final AuthInfoEntity first = new AuthInfoEntity();
        first.setUser(firstEmployee);
        first.setRoles(Collections.singleton(EMPLOYEE));

        final UserEntity secondEmployee = new UserEntity();
        secondEmployee.setId(2L);
        secondEmployee.setFio("Carla Williams");
        secondEmployee.setDepartment("Production");
        secondEmployee.setWages(5070);
        secondEmployee.setWorks(true);
        secondEmployee.setDateStart(LocalDate.of(2018, 1,15));
        secondEmployee.setDateEnd(null);

        final AuthInfoEntity second = new AuthInfoEntity();
        second.setUser(secondEmployee);
        second.setRoles(Collections.singleton(EMPLOYEE));

        final UserEntity thirdEmployee = new UserEntity();
        thirdEmployee.setId(4L);
        thirdEmployee.setFio("Boris Jones");
        thirdEmployee.setDepartment("Production");
        thirdEmployee.setWages(1500);
        thirdEmployee.setWorks(false);
        thirdEmployee.setDateStart(LocalDate.of(2018, 1,15));
        thirdEmployee.setDateEnd(LocalDate.of(2019,10,14));

        final AuthInfoEntity third = new AuthInfoEntity();
        third.setUser(thirdEmployee);
        third.setRoles(Collections.singleton(EMPLOYEE));

        return List.of(first, second, third);
    }

    public static OrderEntity getSaveOrderEntity(final OrderItemEntity firstSaveItem, final OrderItemEntity secondSaveItem) {
        final OrderEntity orderEntity = new OrderEntity();

        orderEntity.setPrice(26.28);
        orderEntity.setItems(List.of(firstSaveItem, secondSaveItem));
        orderEntity.setConsumer(getNewConsumerEntity());

        return orderEntity;
    }

    public static OrderItemEntity getNewOrderItemEntity(final BeerEntity beerEntity, final int liters) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setLiters(liters);
        orderItemEntity.setBeer(beerEntity);

        return orderItemEntity;
    }

    public static UserEntity getAuthNewConsumerEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("vasya@email.com");
        userEntity.setBirthDate(LocalDate.of(1995, 1, 19));
        userEntity.setFio("Пупкин Василий Иванович");
        return userEntity;
    }

    public static UserEntity getAuthNewConsumerEntityForConvert() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(ID);
        userEntity.setDateStart(LocalDate.of(2018, 1,15));
        userEntity.setDateEnd(LocalDate.of(2019,10,14));
        userEntity.setEmail("vasya@email.com");
        userEntity.setBirthDate(LocalDate.of(1995, 1, 19));
        userEntity.setFio("Пупкин Василий Иванович");
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
        userEntity.setWorks(true);
        userEntity.setDateStart(LocalDate.now());

        return userEntity;
    }

    public static UserEntity getAuthNewEmployeeEntity() {
        UserEntity userEntity = getAuthNewConsumerEntity();
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

    public static BeerEntity getSaveBeer(final long id, final int litersInStock) {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setId(id);
        beerEntity.setLitersInStock(litersInStock);
        return beerEntity;
    }

    private static UserEntity getNewConsumerEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(4L);
        userEntity.setFio("Easy Pub");
        return userEntity;
    }

    private static UserEntity getNewEmployeeEntity() {
        final UserEntity employeeEntity = new UserEntity();
        employeeEntity.setId(ID);
        employeeEntity.setId(ID);
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

    private static BeerEntity getNewBeer(final long id, final int litersInStock) {
        final BeerEntity beerEntity = getNewBeer();
        beerEntity.setId(id);
        beerEntity.setLitersInStock(litersInStock);
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
        partRecipeEntity.setIngredient(getNewIngredient(647851));
        return partRecipeEntity;
    }
}
