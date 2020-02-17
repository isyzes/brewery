package com.example.demo.controller;

import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.example.demo.security.Roles.EMPLOYEE;
import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerTest extends AbstractControllerTest {

    @Test
    public void testGetBeersIsOk() throws Exception {
        final String token = signIn(MANAGER);

        given(beerRepository.findAll()).willReturn(ControllerMockData.getBeerEntityList());

        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))

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
    }

    @Test
    public void testUpdatedBeerIsOk() throws Exception {
        final String token = signIn(MANAGER);

        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());

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

        given(beerRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalBeer(3L));

        given(beerRepository.findById(4L)).willReturn(ControllerMockData.getNewOptionalBeer(4L));

        mockMvc.perform(post("/beers/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"consumer\":{\"id\":4,\"name\": \"Easy Pub\"}," +
                        "\"orders\":[" +
                            "{\"beer\":{\"id\":3,\"costPrice\":5415},\"quantity\":5}," +
                            "{\"beer\":{\"id\":4,\"costPrice\":9741},\"quantity\":4}" +
                        "]}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"price\":389.64," +
                        "\"consumer\":{\"id\":4,\"name\": \"Easy Pub\"}," +
                        "\"orders\":[" +
                            "{\"beer\":{\"id\":3,\"costPrice\":5415},\"quantity\":5}," +
                            "{\"beer\":{\"id\":4,\"costPrice\":9741},\"quantity\":4}" +
                        "]}"));
    }

    @Test
    public void testCreatedBeerIsOk() throws Exception {
        final String token = signIn(EMPLOYEE);

        given(beerRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalBeer());

        given(ingredientRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalIngredient());

        mockMvc.perform(post("/beers/created").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBeer\" : \"3\", \"liters\" : 2551}"))

                .andExpect(status().isCreated());
    }

    @Test
    public void testGetBeerListIsEmpty() throws Exception {
        final String token = signIn(MANAGER);

        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
