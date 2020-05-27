package com.example.demo.mapper;

import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.RecipeEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mockdata.MapperMockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public abstract class AbstractMapperTest extends MapperMockData {
    @Autowired
    protected BeerRequestMapper beerRequestMapper;
    @Autowired
    protected EmployeeMapper employeeMapper;
    @Autowired
    protected EmployeeSignUpRequestMapper employeeSignUpRequestMapper;
    @Autowired
    protected IngredientMapper ingredientMapper;
    @Autowired
    protected ListBeerMapper listBeerMapper;
    @Autowired
    protected ListRecipeItemMapper listRecipeItemMapper;
    @Autowired
    protected OrderItemMapper orderItemMapper;
    @Autowired
    protected RecipeMapper recipeMapper;
    @Autowired
    protected RequestOrderMapper requestOrderMapper;
    @Autowired
    protected ResponseOrderMapper responseOrderMapper;

    protected void convertConsumerTest(final UserEntity expected, final UserEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFio(), actual.getFio());
    }

    protected void covertRecipeTest(final RecipeEntity expected, final RecipeEntity actual) {
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

    protected void covertBeerTest(final BeerEntity expected, final BeerEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getFortress(), actual.getFortress(), 0.0001);
        assertEquals(expected.getDateManufacture().toString(), actual.getDateManufacture().toString());
        assertEquals(expected.getShelfLife(), actual.getShelfLife());
        assertEquals(expected.getCostPrice(), actual.getCostPrice());

        covertRecipeTest(expected.getRecipe(), actual.getRecipe());
    }

    protected void covertIngredientTest(final IngredientEntity expected, final IngredientEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getMilligramsInStock(), actual.getMilligramsInStock());
    }
}
