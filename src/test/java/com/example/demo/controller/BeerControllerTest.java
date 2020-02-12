package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BeerControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testGetBeerListIsOk() throws Exception {

        mockMvc.perform(get("/beers/list")
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
    public void testChangeInformationBeerIsOk() throws Exception {

        mockMvc.perform(put("/beers/change/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", \"costPrice\" : 2551}"))

                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\" : 2, " +
                        "\"name\" : \"Grimbergen\" , " +
                        "\"color\" : \"bright\", " +
                        "\"fortress\" : 12.5, " +
                        "\"dateManufacture\": \"12.12.2020\", " +
                        "\"shelfLife\": 25, " +
                        "\"costPrice\": 2551}"));
    }
}
