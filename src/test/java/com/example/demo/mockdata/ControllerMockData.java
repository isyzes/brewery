package com.example.demo.mockdata;

import com.example.demo.entity.*;
import com.example.demo.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.security.Roles.EMPLOYEE;

public class ControllerMockData {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected final static long ID = 3;

    protected static Optional<BeerEntity> getOptionalBeer() {
        return Optional.of(getBeerEntity());
    }

    protected static Optional<BeerEntity> getOptionalBeer(final long id) {
        return Optional.of(getBeerEntity(id));
    }

    protected static Optional<UserEntity> getOptionalEmployeeEntity() {
        return Optional.of(getUserEntity());
    }

    protected static Optional<IngredientEntity> getOptionalIngredient() {
        return Optional.of(getIngredient(647851));
    }

    protected static IngredientEntity getIngredient(final int milligramsInStock) {
        final IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(ID);
        ingredientEntity.setName("Water");
        ingredientEntity.setMilligramsInStock(milligramsInStock);
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

    protected static List<AuthInfoEntity> getAuthInfoEntityList() {
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

    protected static OrderEntity getOrderEntity(final OrderItemEntity firstSaveItem, final OrderItemEntity secondSaveItem) {
        final OrderEntity orderEntity = new OrderEntity();
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(4L);
        userEntity.setFio("Easy Pub");

        orderEntity.setPrice(102.04);
        orderEntity.setItems(List.of(firstSaveItem, secondSaveItem));
        orderEntity.setConsumer(userEntity);

        return orderEntity;
    }

    protected static OrderItemEntity getOrderItemEntity(final BeerEntity beerEntity, final int liters) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setLiters(liters);
        orderItemEntity.setBeer(beerEntity);

        return orderItemEntity;
    }

    protected static UserEntity getUserEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("vasya@email.com");
        userEntity.setBirthDate(LocalDate.of(1995, 1, 19));
        userEntity.setFio("Пупкин Василий Иванович");
        return userEntity;
    }

    protected static UserEntity getEmployeeEntityForRequest() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setFio("Ivanov Ivan Ivanovich");
        userEntity.setDepartment("Production");
        userEntity.setWages(2020);

        return userEntity;
    }

    protected static UserEntity getEmployeeEntityForResponse() {
        final UserEntity userEntity = getEmployeeEntityForRequest();
        userEntity.setId(0L);
        userEntity.setWorks(true);
        userEntity.setDateStart(LocalDate.now());

        return userEntity;
    }

    protected static UserEntity getUserEntityToDismiss() {
        UserEntity userEntity = getUserEntity();
        userEntity.setWorks(false);
        userEntity.setDateEnd(LocalDate.now());

        return userEntity;
    }

    protected static BeerEntity getBeerEntity() {
        final BeerEntity beerEntity = new BeerEntity();
        beerEntity.setName("Grimbergen");
        beerEntity.setCostPrice(2551);
        beerEntity.setId(ID);
        beerEntity.setColor("bright");
        beerEntity.setFortress(12.5);
        beerEntity.setDateManufacture(LocalDate.of(2020, 12, 12));
        beerEntity.setShelfLife(25);
        beerEntity.setLitersInStock(6225);
        beerEntity.setRecipe(getRecipeEntity());
        return beerEntity;
    }

    protected static BeerEntity getSaveBeerEntity() {
        final BeerEntity beerEntity = getBeerEntity();
        beerEntity.setLitersInStock(beerEntity.getLitersInStock() + 2551);
        return beerEntity;
    }

    protected static BeerEntity getSaveBeerEntity(final long id, final int litersInStock) {
        final BeerEntity beerEntity = getBeerEntity();
        beerEntity.setId(id);
        beerEntity.setLitersInStock(litersInStock);
        return beerEntity;
    }

    protected static BeerEntity getBeerEntity(final long id) {
        final BeerEntity beerEntity = getBeerEntity();
        beerEntity.setId(id);
        return beerEntity;
    }

    protected static RecipeEntity getRecipeEntity() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(ID);
        recipeEntity.setItems(List.of(getNewPartRecipeEntity()));
        return recipeEntity;
    }

    protected static RecipeItemEntity getNewPartRecipeEntity() {
        final RecipeItemEntity partRecipeEntity = new RecipeItemEntity();
        partRecipeEntity.setId(ID);
        partRecipeEntity.setMilligram(5);
        partRecipeEntity.setIngredient(getIngredient(647851));
        return partRecipeEntity;
    }

    protected AuthInfoEntity createAuthInfo(final Roles roles) {
        final UserEntity user = new UserEntity();
        user.setEmail("vasya@email.com");

        final AuthInfoEntity authInfo = new AuthInfoEntity();
        authInfo.setLogin(user.getEmail());
        authInfo.setPassword(passwordEncoder.encode("qwerty"));
        authInfo.setUser(user);
        authInfo.setRoles(Collections.singleton(roles));
        return authInfo;
    }

    protected AuthInfoEntity createAuthInfo(final Roles roles, final UserEntity user) {
        final AuthInfoEntity authInfo =createAuthInfo(roles);
        authInfo.setRoles(Collections.singleton(roles));
        authInfo.setUser(user);
        return authInfo;
    }
}
