package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SalesControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testBuyingBeerIsOk() throws Exception {

        mockMvc.perform(post("/buy/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[" +
                        "{\"idBeer\" : 1, \"quantity\" : 5}," +
                        "{\"idBeer\" : 4, \"quantity\" : 4}" +
                        "]"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"id\":4," +
                        "\"price\":16.0," +
                        "\"orders\":[" +
                            "{\"id\":0,\"idBeer\":1,\"quantity\":5}," +
                            "{\"id\":0,\"idBeer\":4,\"quantity\":4}" +
                        "]}"));
    }
}
