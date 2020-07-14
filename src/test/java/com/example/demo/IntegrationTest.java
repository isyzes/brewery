package com.example.demo;

import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.dto.beer.Beer;
import com.example.demo.dto.beer.OrderCreatedBeer;
import com.example.demo.dto.ingredient.IngredientRequestOrder;
import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.dto.recipe.RecipeItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.hasLength;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    public void integrationTest() throws Exception {
        signUpUser();

        final String tokenUser = signIn("vasya@email.com", "qwerty");
        final String tokenAdmin = signIn("admin@email.com", "12345");

        final List<Beer> beers = getBeers(tokenUser);
        final String request = getRequestOrder(beers);
        final ResponseOrder response = buyBeer(tokenAdmin, tokenUser, beers, request);

        assertTrue(response.getPrice() > 0.00);
    }

    private ResponseOrder buyBeer(final String tokenAdmin, final String tokenUser, final List<Beer> beers, final String request) throws Exception {
        ResponseOrder response = buyBeer(tokenUser, request);

        boolean needBeer = checkEnoughBeer(response);

        while (needBeer) {
            for (Beer beer: beers) {
                final String requestForUpdatedLitersBeer = getRequestForUpdatedLitersBeer(beer);
                updatedIngredientInStock(tokenAdmin, requestForUpdatedLitersBeer, beer);
            }

            response = buyBeer(tokenUser, request);
            needBeer = checkEnoughBeer(response);
        }

        return response;
    }

    private void updatedIngredientInStock(final String tokenAdmin, final String requestForUpdatedLitersBeer, final Beer beer) throws Exception {
        boolean needIngredient = checkEnoughIngredient(tokenAdmin, requestForUpdatedLitersBeer);

        while (needIngredient) {
            for (RecipeItem recipeItem: beer.getRecipe().getItems()) {
                final String ingredientRequestOrder = getIngredientRequestOrder(recipeItem.getIngredient().getId());
                buyIngredient(tokenAdmin, ingredientRequestOrder);
                needIngredient = checkEnoughIngredient(tokenAdmin, requestForUpdatedLitersBeer);
            }
        }
    }

    private boolean checkEnoughBeer(final ResponseOrder response) {
        return response.getItems() == null && response.getPrice() == 0.0;
    }

    private String getIngredientRequestOrder(long id) throws JsonProcessingException {
        IngredientRequestOrder ingredientRequestOrder = new IngredientRequestOrder();
        ingredientRequestOrder.setIdIngredient(id);
        ingredientRequestOrder.setMilligramsInStock(1000000000);

        return objectMapper.writeValueAsString(ingredientRequestOrder);
    }

    private String getRequestForUpdatedLitersBeer(Beer beer) throws JsonProcessingException {
        final OrderCreatedBeer orderCreatedBeer = new OrderCreatedBeer();
        orderCreatedBeer.setIdBeer(beer.getId());
        orderCreatedBeer.setLiters(100000);

        return objectMapper.writeValueAsString(orderCreatedBeer);
    }

    private String getRequestOrder(final List<Beer> beers) throws JsonProcessingException {
        final List<OrderItem> items = new ArrayList<>();
        final Random random = new Random();

        for (Beer beer: beers) {
            if (random.nextBoolean()) {
                final OrderItem orderItem = new OrderItem();
                orderItem.setBeer(beer);
                orderItem.setLiters(555555);
                items.add(orderItem);
            }
        }

        if (items.size() == 0) {
            return getRequestOrder(beers);
        }
        else {
            final RequestOrder result = new RequestOrder();
            result.setItems(items);
            return objectMapper.writeValueAsString(result);
        }
    }

    private void signUpUser() throws Exception {
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"" + "vasya@email.com" + "\",\n" +
                        "  \"password\" : \"" + "qwerty" +"\",\n" +
                        "  \"fio\" : \"Пупкин Василий Иванович\",\n" +
                        "  \"birthDate\" : \"19.01.1995\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token", hasLength(144)));
    }

    private String signIn(final String login, final String password) throws Exception {
        final String response = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"" + login + "\",\n" +
                        "  \"password\" : \"" + password + "\""+
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token", hasLength(144)))
                .andReturn().getResponse().getContentAsString();

        return "Bearer " + objectMapper.readValue(response, UserSignInResponse.class).getToken();
    }

    private List<Beer> getBeers(final String token) throws Exception {
        final String response = mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final TypeFactory factory = objectMapper.getTypeFactory();
        final CollectionType listType = factory.constructCollectionType(List.class, Beer.class);

        return objectMapper.readValue(response, listType);
    }

    private ResponseOrder buyBeer(final String token, final String request) throws Exception {
        final String response = mockMvc.perform(post("/beers/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, ResponseOrder.class);
    }

    private boolean checkEnoughIngredient(final String token, final String request) throws Exception {
        final int status = mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn().getResponse().getStatus();

        return status != 200;
    }

    private void buyIngredient(final String token, String request) throws Exception {
        mockMvc.perform(put("/ingredients/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }
}
