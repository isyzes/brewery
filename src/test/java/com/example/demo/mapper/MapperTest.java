package com.example.demo.mapper;

import com.example.demo.dto.Employee;
import com.example.demo.dto.authentication.UserSignUpRequest;
import com.example.demo.dto.beer.Beer;
import com.example.demo.dto.ingredient.Ingredient;
import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.dto.recipe.Recipe;
import com.example.demo.dto.recipe.RecipeItem;
import com.example.demo.entity.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class MapperTest extends AbstractMapperTest {
    @Test
    public void beerRequestMapperTest() {
        final BeerEntity expected = getBeerEntity();
        final Beer beer = beerRequestMapper.destinationToSource(expected);
        final BeerEntity actual = beerRequestMapper.sourceToDestination(beer);

        covertBeerTest(expected, actual);
    }

    @Test
    public void beerRequestMapperNullTest() {
        final Beer beer = beerRequestMapper.destinationToSource(null);
        final BeerEntity actual = beerRequestMapper.sourceToDestination(beer);

        assertNull(actual);
    }

    @Test
    public void employeeMapperTest() {
        final UserEntity expected = getUserEntity();
        final Employee employee = employeeMapper.destinationToSource(expected);
        final UserEntity actual = employeeMapper.sourceToDestination(employee);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFio(), actual.getFio());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getWages(), actual.getWages(), 0.01);
        //assertEquals(expected.isWorks(), actual.isWorks());
        assertEquals(expected.getDateStart().toString(), actual.getDateStart().toString());
        assertEquals(expected.getDateEnd().toString(), actual.getDateEnd().toString());
    }

    @Test
    public void employeeMapperNullTest() {
        final Employee employee = employeeMapper.destinationToSource(null);
        final UserEntity actual = employeeMapper.sourceToDestination(employee);

        assertNull(actual);
    }

    @Test
    public void employeeSignUpRequestMapperTest() {
        final UserEntity expected = getUserEntity();
        final UserSignUpRequest userSignUpRequest = employeeSignUpRequestMapper.destinationToSource(expected);
        final UserEntity actual = employeeSignUpRequestMapper.sourceToDestination(userSignUpRequest);

        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFio(), actual.getFio());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getSelfDescription(), actual.getSelfDescription());
    }

    @Test
    public void employeeSignUpRequestMapperNullTest() {
        final UserSignUpRequest userSignUpRequest = employeeSignUpRequestMapper.destinationToSource(null);
        final UserEntity actual = employeeSignUpRequestMapper.sourceToDestination(userSignUpRequest);

        assertNull(actual);
    }

    @Test
    public void ingredientMapperTest() {
        final IngredientEntity expected = getIngredientEntity();
        final Ingredient ingredient = ingredientMapper.destinationToSource(expected);
        final IngredientEntity actual = ingredientMapper.sourceToDestination(ingredient);

        covertIngredientTest(expected, actual);
    }

    @Test
    public void ingredientMapperNullTest() {
        final Ingredient ingredient = ingredientMapper.destinationToSource(null);
        final IngredientEntity actual = ingredientMapper.sourceToDestination(ingredient);

        assertNull(actual);
    }

    @Test
    public void listBeerMapperTest() {
        final List<BeerEntity> expected = getBeerEntityList();
        final List<Beer> beers = listBeerMapper.destinationToSource(expected);
        final List<BeerEntity> actual = listBeerMapper.sourceToDestination(beers);

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            covertBeerTest(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void listBeerMapperNullTest() {
        final List<Beer> beers = listBeerMapper.destinationToSource(null);
        final List<BeerEntity> actual = listBeerMapper.sourceToDestination(beers);

        assertNull(actual);
    }

    @Test
    public void listRecipeItemMapperTest() {
        final List<RecipeItemEntity> expected = getRecipeItemEntityList();
        final List<RecipeItem> recipeItems = listRecipeItemMapper.destinationToSource(expected);
        final List<RecipeItemEntity> actual = listRecipeItemMapper.sourceToDestination(recipeItems);

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getMilligram(), actual.get(i).getMilligram());

            covertIngredientTest(expected.get(i).getIngredient(), actual.get(i).getIngredient());
        }
    }

    @Test
    public void listRecipeItemMapperNullTest() {
        final List<RecipeItem> recipeItems = listRecipeItemMapper.destinationToSource(null);
        final List<RecipeItemEntity> actual = listRecipeItemMapper.sourceToDestination(recipeItems);

        assertNull(actual);
    }

    @Test
    public void orderItemMapperTest() {
        final OrderItemEntity expected = getOrderItemEntity();
        final OrderItem orderItem = orderItemMapper.destinationToSource(expected);
        final OrderItemEntity actual = orderItemMapper.sourceToDestination(orderItem);

        assertEquals(expected.getLiters(), actual.getLiters());
        covertBeerTest(expected.getBeer(), actual.getBeer());
        covertRecipeTest(expected.getBeer().getRecipe(), actual.getBeer().getRecipe());
    }

    @Test
    public void orderItemMapperNullTest() {
        final OrderItem orderItem = orderItemMapper.destinationToSource(null);
        final OrderItemEntity actual = orderItemMapper.sourceToDestination(orderItem);

        assertNull(actual);
    }

    @Test
    public void recipeMapperTest() {
        final RecipeEntity expected = getRecipeEntity();
        final Recipe recipe = recipeMapper.destinationToSource(expected);
        final RecipeEntity actual = recipeMapper.sourceToDestination(recipe);

        covertRecipeTest(expected, actual);
    }

    @Test
    public void recipeMapperNullTest() {
        final Recipe recipe = recipeMapper.destinationToSource(null);
        final RecipeEntity actual = recipeMapper.sourceToDestination(recipe);

        assertNull(actual);
    }

    @Test
    public void requestOrderMapperTest() {
        final OrderEntity expected = getOrderEntity();
        final RequestOrder requestOrder = requestOrderMapper.destinationToSource(expected);
        final OrderEntity actual = requestOrderMapper.sourceToDestination(requestOrder);

//        convertConsumerTest(expected.getConsumer(), actual.getConsumer());
        assertEquals(expected.getItems().size(), actual.getItems().size());

        for (int i = 0; i < expected.getItems().size(); i++) {
            assertEquals(expected.getItems().get(i).getLiters(),  actual.getItems().get(i).getLiters());
            covertBeerTest(expected.getItems().get(i).getBeer(),  actual.getItems().get(i).getBeer());
        }
    }

    @Test
    public void requestOrderMapperNullTest() {
        final RequestOrder requestOrder = requestOrderMapper.destinationToSource(null);
        final OrderEntity actual = requestOrderMapper.sourceToDestination(requestOrder);

        assertNull(actual);
    }

    @Test
    public void responseOrderMapperTest() {
        final OrderEntity expected = getOrderEntity();
        final ResponseOrder responseOrder = responseOrderMapper.destinationToSource(expected);
        final OrderEntity actual = responseOrderMapper.sourceToDestination(responseOrder);

        assertEquals(expected.getPrice(), actual.getPrice(), 0.01);
        assertEquals(expected.getItems().size(), actual.getItems().size());
//        convertConsumerTest(expected.getConsumer(), actual.getConsumer());

        for (int i = 0; i < expected.getItems().size(); i++) {
            assertEquals(expected.getItems().get(i).getLiters(),  actual.getItems().get(i).getLiters());
            covertBeerTest(expected.getItems().get(i).getBeer(),  actual.getItems().get(i).getBeer());
        }
    }

    @Test
    public void responseOrderMapperNullTest() {
        final ResponseOrder responseOrder = responseOrderMapper.destinationToSource(null);
        final OrderEntity actual = responseOrderMapper.sourceToDestination(responseOrder);

        assertNull(actual);
    }
}
