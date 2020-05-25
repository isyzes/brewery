package com.example.demo.controller;

import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.RecipeEntity;
import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.Roles.CONSUMER;
import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerTest extends AbstractControllerTest {

    @Test
    void testGetBeersIsOk() throws Exception {
        // given
        given(beerRepository.findAll()).willReturn(ControllerMockData.getBeerEntityList());
        // when
        final String token = signIn(CONSUMER);
        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{" +
                        "\"id\":1," +
                        "\"name\":\"Garage\"," +
                        "\"color\":\"bright\"," +
                        "\"fortress\":12.5," +
                        "\"dateManufacture\":\"12.12.2020\"," +
                        "\"shelfLife\":25," +
                        "\"costPrice\":574," +
                        "\"recipe\":null" +
                        "}," +
                        "{" +
                        "\"id\":2," +
                        "\"name\":\"Miller\"," +
                        "\"color\":\"bright\"," +
                        "\"fortress\":12.5," +
                        "\"dateManufacture\":\"12.12.2020\"," +
                        "\"shelfLife\":25," +
                        "\"costPrice\":755," +
                        "\"recipe\":null" +
                        "}," +
                        "{" +
                        "\"id\":3," +
                        "\"name\":\"Heineken\"," +
                        "\"color\":\"dark\"," +
                        "\"fortress\":9.5," +
                        "\"dateManufacture\":\"12.12.2020\"," +
                        "\"shelfLife\":35," +
                        "\"costPrice\":5756," +
                        "\"recipe\":null" +
                        "}]"));
        verify(beerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testUpdatedBeerIsOk() throws Exception {
        // given
        final BeerEntity save = ControllerMockData.getResponseUpdatedBeer();
        final RecipeEntity saveRecipe = ControllerMockData.getRecipeEntity();
        given(beerRepository.findById(ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        given(recipeRepository.save(saveRecipe)).willReturn(saveRecipe);
        given(beerRepository.save(save)).willReturn(save);
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", " +
                        "\"costPrice\" : 2551, " +
                        "\"recipe\": {\"id\" : 3, \"items\" : [{\"id\" : 3, \"ingredient\" : {\"id\" : 3, \"name\" : \"Water\",  \"milligramsInStock\" : 647851}, \"milligram\" : 5}]}}"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\" : 3, " +
                        "\"name\" : \"Grimbergen\" , " +
                        "\"color\" : \"bright\", " +
                        "\"fortress\" : 12.5, " +
                        "\"dateManufacture\": \"12.12.2020\", " +
                        "\"shelfLife\": 25, " +
                        "\"costPrice\": 2551}"));
        verify(beerRepository, Mockito.times(1)).findById(ID);
        verify(beerRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testUpdatedBeerIs() throws Exception {
        // given
        given(beerRepository.findById(ID)).willReturn(Optional.empty());
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", \"costPrice\" : 2551}"))
                // then
                .andExpect(status().isBadRequest());
        verify(beerRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    void testBuyBeerIsOk() throws Exception {
        // given
        final BeerEntity firstSaveBeer = ControllerMockData.getSaveBeer(3, 6225- 5);
        final BeerEntity secondSaveBeer = ControllerMockData.getSaveBeer(4, 6225- 4);
        final OrderItemEntity firstSaveItem = ControllerMockData.getNewOrderItemEntity(firstSaveBeer, 5);
        final OrderItemEntity secondSaveItem = ControllerMockData.getNewOrderItemEntity(secondSaveBeer, 4);
        final OrderEntity save = ControllerMockData.getSaveOrderEntity(firstSaveItem, secondSaveItem);
        given(beerRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalBeer(3));
        given(beerRepository.findById(4L)).willReturn(ControllerMockData.getNewOptionalBeer(4));
        given(beerRepository.save(firstSaveBeer)).willReturn(firstSaveBeer);
        given(beerRepository.save(secondSaveBeer)).willReturn(secondSaveBeer);
        given(orderItemRepository.save(firstSaveItem)).willReturn(firstSaveItem);
        given(orderItemRepository.save(secondSaveItem)).willReturn(secondSaveItem);
        given(orderRepository.save(save)).willReturn(save);
        final String token = signIn(CONSUMER);
        // when
        mockMvc.perform(post("/beers/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"consumer\":{\"id\":4,\"fio\": \"Easy Pub\"}," +
                        "\"items\":[" +
                            "{\"beer\":{\"id\" : 3}, \"liters\":5}," +
                            "{\"beer\":{\"id\" : 4}, \"liters\":4}" +
                        "]}"))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"price\":26.28," +
                        "\"consumer\":{\"id\":4,\"fio\": \"Easy Pub\"}," +
                        "\"items\":[" +
                            "{\"beer\":{\"id\":3, \"name\":\"Grimbergen\"},\"liters\":5}," +
                            "{\"beer\":{\"id\":4, \"name\":\"Grimbergen\"},\"liters\":4}" +
                        "]}"));
        verify(beerRepository, Mockito.times(1)).findById(3L);
        verify(beerRepository, Mockito.times(1)).findById(4L);
        verify(beerRepository, Mockito.times(1)).save(firstSaveBeer);
        verify(beerRepository, Mockito.times(1)).save(secondSaveBeer);
        verify(orderItemRepository, Mockito.times(1)).save(firstSaveItem);
        verify(orderItemRepository, Mockito.times(1)).save(secondSaveItem);
        verify(orderRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testBuyBeerIsBadRequest() throws Exception {
        // given
        given(beerRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalBeer(3L));
        given(beerRepository.findById(4L)).willReturn(ControllerMockData.getNewOptionalBeer(4L));
        final String token = signIn(CONSUMER);
        // when
        mockMvc.perform(post("/beers/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"consumer\":{\"id\":4,\"fio\": \"Easy Pub\"}," +
                        "\"items\":[" +
                        "{\"beer\":{\"id\" : 3}, \"liters\":5435235}," +
                        "{\"beer\":{\"id\" : 4}, \"liters\":9234344}" +
                        "]}"))
                // then
                .andExpect(status().isBadRequest());
        verify(beerRepository, Mockito.times(1)).findById(3L);
    }

    @Test
    void testUpdatedLitersBeerInStockIsOk() throws Exception {
        // given
        final BeerEntity save = ControllerMockData.getSaveBeer();
        given(beerRepository.findById(ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        given(ingredientRepository.findById(ID)).willReturn(ControllerMockData.getNewOptionalIngredient());
        given(beerRepository.save(save)).willReturn(save);
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : 3, \"liters\" : 2551}"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\"idBeer\" : 3, \"nameBeer\" : \"Grimbergen\", \"totalLiters\" : 8776}"));
        verify(beerRepository, Mockito.times(1)).findById(ID);
        verify(ingredientRepository, Mockito.times(1)).findById(ID);
        verify(beerRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testUpdatedLitersBeerInStockIsBadRequest() throws Exception {
        // given
        given(beerRepository.findById(ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        given(ingredientRepository.findById(ID)).willReturn(ControllerMockData.getNewOptionalIngredient());
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : 3, \"liters\" : 2147483647}"))
                // then
                .andExpect(status().isBadRequest());
        verify(beerRepository, Mockito.times(1)).findById(ID);
        verify(ingredientRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    void testUpdatedLitersBeerInStockIsBadRequest2() throws Exception {
        // given
        given(beerRepository.findById(ID)).willReturn(Optional.empty());
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : 3, \"liters\" : 2147483647}"))
                // then
                .andExpect(status().isBadRequest());
        verify(beerRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    void testGetBeerListIsEmpty() throws Exception {
        // given
        given(beerRepository.findAll()).willReturn(List.of());
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(beerRepository, Mockito.times(1)).findAll();
    }
}