package com.example.demo.controller;

import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import java.util.ArrayList;
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
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findAll()).willReturn(ControllerMockData.getBeerEntityList());

        // when
        final String token = signIn(MANAGER);
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
        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");

    }

    @Test
    void testUpdatedBeerIsOk() throws Exception {
        // given
        final BeerEntity save = ControllerMockData.getResponseUpdatedBeer();
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        given(beerRepository.save(save)).willReturn(save);

        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", \"costPrice\" : 2551}"))
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

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(1)).findById(ControllerMockData.ID);
        verify(beerRepository, Mockito.times(1)).save(save);

    }

    @Test
    void testUpdatedBeerIs() throws Exception {
        // given
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(ControllerMockData.ID)).willReturn(Optional.empty());
        final String token = signIn(MANAGER);

        // when
        mockMvc.perform(put("/beers/updated/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", \"costPrice\" : 2551}"))
        // then
                .andExpect(status().isBadRequest());

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(1)).findById(ControllerMockData.ID);
    }

    @Test
    void testSellBeerIsOk() throws Exception {
        // given
        final OrderEntity save = ControllerMockData.getNewOrderEntity();
        final OrderItemEntity firstSaveItem = ControllerMockData.getNewOrderItemEntity(3L, 5);
        final OrderItemEntity secondSaveItem = ControllerMockData.getNewOrderItemEntity(4L, 4);

        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalBeer(3L));
        given(beerRepository.findById(4L)).willReturn(ControllerMockData.getNewOptionalBeer(4L));
        given(orderItemRepository.save(firstSaveItem)).willReturn(firstSaveItem);
        given(orderItemRepository.save(secondSaveItem)).willReturn(secondSaveItem);
        given(orderRepository.save(save)).willReturn(save);

        // when
        final String token = signIn(CONSUMER);
        mockMvc.perform(post("/beers/sell").header("Authorization", token)
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

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(4)).findById(3L);
        verify(beerRepository, Mockito.times(4)).findById(4L);
        verify(orderItemRepository, Mockito.times(1)).save(firstSaveItem);
        verify(orderItemRepository, Mockito.times(1)).save(secondSaveItem);
        verify(orderRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testSellBeerIsBadRequest() throws Exception {
        // given
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalBeer(3L));
        given(beerRepository.findById(4L)).willReturn(ControllerMockData.getNewOptionalBeer(4L));

        final String token = signIn(CONSUMER);
        // when
        mockMvc.perform(post("/beers/sell").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"consumer\":{\"id\":4,\"fio\": \"Easy Pub\"}," +
                        "\"items\":[" +
                        "{\"beer\":{\"id\" : 3}, \"liters\":5435235}," +
                        "{\"beer\":{\"id\" : 4}, \"liters\":9234344}" +
                        "]}"))
        // then
                .andExpect(status().isBadRequest());

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(1)).findById(3L);
    }

    @Test
    void testUpdatedLitersBeerInStockIsOk() throws Exception {
        // given
        final BeerEntity save = ControllerMockData.getSaveBeer();

        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        given(ingredientRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalIngredient());
        given(beerRepository.save(save)).willReturn(save);

        final String token = signIn(MANAGER);

        // when
        mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : 3, \"liters\" : 2551}"))
        // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\"idBeer\" : 3, \"nameBeer\" : \"Grimbergen\", \"totalLiters\" : 8776}}"));

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(1)).findById(ControllerMockData.ID);
        verify(ingredientRepository, Mockito.times(2)).findById(ControllerMockData.ID);
        verify(beerRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testUpdatedLitersBeerInStockIsBadRequest() throws Exception {
        // given
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        given(ingredientRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalIngredient());

        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : 3, \"liters\" : 2147483647}"))
        // then
                .andExpect(status().isBadRequest());

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(1)).findById(ControllerMockData.ID);
        verify(ingredientRepository, Mockito.times(1)).findById(ControllerMockData.ID);
    }

    @Test
    void testGetBeerListIsEmpty() throws Exception {
        // given
        final List<BeerEntity> list = new ArrayList<>();
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findAll()).willReturn(list);

        final String token = signIn(MANAGER);

        // when
        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
        // then
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(userRepository, Mockito.times(1)).findAllByEmail("vasya@email.com");
        verify(beerRepository, Mockito.times(1)).findAll();
    }
}
