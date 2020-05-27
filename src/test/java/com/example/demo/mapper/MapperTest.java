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
import com.example.demo.mockdata.MapperMockData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class MapperTest {
    @Autowired
    private BeerRequestMapper beerRequestMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeSignUpRequestMapper employeeSignUpRequestMapper;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private ListBeerMapper listBeerMapper;

    @Autowired
    private ListRecipeItemMapper listRecipeItemMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RequestOrderMapper requestOrderMapper;

    @Autowired
    private ResponseOrderMapper responseOrderMapper;

    @Test
    public void beerRequestMapperTest() {
        final BeerEntity expected = MapperMockData.getSaveBeer();
        final Beer beer = beerRequestMapper.destinationToSource(expected);
        final BeerEntity actual = beerRequestMapper.sourceToDestination(beer);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getFortress(), actual.getFortress(), 0.0001);
        assertEquals(expected.getDateManufacture().toString(), actual.getDateManufacture().toString());
        assertEquals(expected.getShelfLife(), actual.getShelfLife());
        assertEquals(expected.getCostPrice(), actual.getCostPrice());
        assertEquals(expected.getRecipe(), actual.getRecipe());
    }

    @Test
    public void beerRequestMapperNullTest() {
        final Beer beer = beerRequestMapper.destinationToSource(null);
        final BeerEntity actual = beerRequestMapper.sourceToDestination(beer);

        assertNull(actual);
    }

    @Test
    public void employeeMapperTest() {
        final UserEntity expected = MapperMockData.getAuthNewConsumerEntityForConvert();
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
        final UserEntity expected = MapperMockData.getAuthNewConsumerEntityForConvert();
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
        final IngredientEntity expected = MapperMockData.getNewIngredient(974);
        final Ingredient ingredient = ingredientMapper.destinationToSource(expected);
        final IngredientEntity actual = ingredientMapper.sourceToDestination(ingredient);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getMilligramsInStock(), actual.getMilligramsInStock());
    }

    @Test
    public void ingredientMapperNullTest() {
        final Ingredient ingredient = ingredientMapper.destinationToSource(null);
        final IngredientEntity actual = ingredientMapper.sourceToDestination(ingredient);

        assertNull(actual);
    }

    @Test
    public void listBeerMapperTest() {
        final List<BeerEntity> expected = MapperMockData.getBeerEntityListForConverter();
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
        final List<RecipeItemEntity> expected = List.of(MapperMockData.getNewPartRecipeEntity());
        final List<RecipeItem> recipeItems = listRecipeItemMapper.destinationToSource(expected);
        final List<RecipeItemEntity> actual = listRecipeItemMapper.sourceToDestination(recipeItems);

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getMilligram(), actual.get(i).getMilligram());
            assertEquals(expected.get(i).getIngredient().getId(), actual.get(i).getIngredient().getId());
            assertEquals(expected.get(i).getIngredient().getName(), actual.get(i).getIngredient().getName());
            assertEquals(expected.get(i).getIngredient().getMilligramsInStock(), actual.get(i).getIngredient().getMilligramsInStock());
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
        final OrderItemEntity expected = MapperMockData.getNewOrderItemEntity(MapperMockData.getSaveBeer(), 6445);
        final OrderItem orderItem = orderItemMapper.destinationToSource(expected);
        final OrderItemEntity actual = orderItemMapper.sourceToDestination(orderItem);

        assertEquals(expected.getLiters(), actual.getLiters());
        assertEquals(expected.getBeer().getId(), actual.getBeer().getId());
        assertEquals(expected.getBeer().getName(), actual.getBeer().getName());
        assertEquals(expected.getBeer().getColor(), actual.getBeer().getColor());
        assertEquals(expected.getBeer().getFortress(), actual.getBeer().getFortress(), 0.0001);
        assertEquals(expected.getBeer().getDateManufacture().toString(), actual.getBeer().getDateManufacture().toString());
        assertEquals(expected.getBeer().getShelfLife(), actual.getBeer().getShelfLife());

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
        final RecipeEntity expected = MapperMockData.getRecipeEntity();
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
        final OrderEntity expected = MapperMockData.getSaveOrderEntity(MapperMockData.getNewOrderItemEntity(MapperMockData.getSaveBeer(), 57), MapperMockData.getNewOrderItemEntity(MapperMockData.getSaveBeer(12, 3), 434));
        final RequestOrder requestOrder = requestOrderMapper.destinationToSource(expected);
        final OrderEntity actual = requestOrderMapper.sourceToDestination(requestOrder);

        convertConsumerTest(expected.getConsumer(), actual.getConsumer());
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
        final OrderEntity expected = MapperMockData.getSaveOrderEntity(MapperMockData.getNewOrderItemEntity(MapperMockData.getSaveBeer(), 57), MapperMockData.getNewOrderItemEntity(MapperMockData.getSaveBeer(12, 3), 434));
        final ResponseOrder responseOrder = responseOrderMapper.destinationToSource(expected);
        final OrderEntity actual = responseOrderMapper.sourceToDestination(responseOrder);

        assertEquals(expected.getPrice(), actual.getPrice(), 0.01);
        assertEquals(expected.getItems().size(), actual.getItems().size());
        convertConsumerTest(expected.getConsumer(), actual.getConsumer());

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

    private void convertConsumerTest(final UserEntity expected, final UserEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFio(), actual.getFio());
    }

    private void covertRecipeTest(final RecipeEntity expected, final RecipeEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItems().size(), actual.getItems().size());

        for (int i = 0; i < expected.getItems().size(); i++) {
            assertEquals(expected.getItems().get(i).getId(), actual.getItems().get(i).getId());
            assertEquals(expected.getItems().get(i).getMilligram(), actual.getItems().get(i).getMilligram());

            assertEquals(expected.getItems().get(i).getIngredient().getId(), actual.getItems().get(i).getIngredient().getId());
            assertEquals(expected.getItems().get(i).getIngredient().getName(), actual.getItems().get(i).getIngredient().getName());
            assertEquals(expected.getItems().get(i).getIngredient().getMilligramsInStock(), actual.getItems().get(i).getIngredient().getMilligramsInStock());
        }
    }

    private void covertBeerTest(final BeerEntity expected, final BeerEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getFortress(), actual.getFortress(), 0.0001);
        assertEquals(expected.getDateManufacture().toString(), actual.getDateManufacture().toString());
        assertEquals(expected.getShelfLife(), actual.getShelfLife());
        assertEquals(expected.getCostPrice(), actual.getCostPrice());

        covertRecipeTest(expected.getRecipe(), actual.getRecipe());
    }
}
