package com.example.demo.controller;

import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.example.demo.security.Roles.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerTest extends AbstractControllerTest {

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
    }

    @Test
    public void testUpdatedBeerIsOk() throws Exception {


        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());
        final String token = signIn(MANAGER);
        mockMvc.perform(put("/beers/updated/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", \"costPrice\" : 2551}"))

                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\" : 3, " +
                        "\"name\" : \"Grimbergen\" , " +
                        "\"color\" : \"bright\", " +
                        "\"fortress\" : 12.5, " +
                        "\"dateManufacture\": \"12.12.2020\", " +
                        "\"shelfLife\": 25, " +
                        "\"costPrice\": 2551}"));


    }

    @Test
    public void testSellBeerIsOk() throws Exception {


        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());

        given(beerRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalBeer(3L));

        given(beerRepository.findById(4L)).willReturn(ControllerMockData.getNewOptionalBeer(4L));

        given(orderRepository.save(ControllerMockData.getNewOrderEntity())).willReturn(ControllerMockData.getNewOrderEntity());
        final String token = signIn(CONSUMER);
        mockMvc.perform(post("/beers/sell").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"consumer\":{\"id\":4,\"fio\": \"Easy Pub\"}," +
                        "\"items\":[" +
                            "{\"idBeer\":3, \"liters\":5}," +
                            "{\"idBeer\":4, \"liters\":4}" +
                        "]}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"price\":26.28," +
                        "\"consumer\":{\"id\":4,\"fio\": \"Easy Pub\"}," +
                        "\"items\":[" +
                            "{\"idBeer\":3,\"liters\":5}," +
                            "{\"idBeer\":4,\"liters\":4}" +
                        "]}"));
    }

    @Test
    public void testUpdatedLitersBeerInStockIsOk() throws Exception {


        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());

        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());

        given(ingredientRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalIngredient());
        final String token = signIn(MANAGER);
        mockMvc.perform(put("/beers/updated").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : 3, \"liters\" : 2551}"))

                .andExpect(status().isOk())
                .andExpect(content().json("{\"idBeer\" : 3, \"nameBeer\" : \"Grimbergen\", \"totalLiters\" : 8776}}"));
    }

    @Test
    public void testGetBeerListIsEmpty() throws Exception {
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        final String token = signIn(MANAGER);

        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
