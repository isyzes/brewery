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

        mockMvc.perform(post("/sell/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[" +
                        "{\"beer\" : {\"id\" : 1, \"costPrice\" : 5415}, \"quantity\" : 5}," +
                        "{\"beer\" : {\"id\" : 4, \"costPrice\" : 9741}, \"quantity\" : 4}" +
                        "]"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"id\":4," +
                        "\"price\":389.64," +
                        "\"consumers\":{\"id\":4,\"name\":null}," +
                        "\"listOrder\":[" +
                            "{\"id\":0,\"beer\":{\"id\":1,\"name\":null,\"color\":null,\"fortress\":0.0,\"dateManufacture\":null,\"shelfLife\":0,\"costPrice\":5415,\"recipe\":null},\"quantity\":5}," +
                            "{\"id\":0,\"beer\":{\"id\":4,\"name\":null,\"color\":null,\"fortress\":0.0,\"dateManufacture\":null,\"shelfLife\":0,\"costPrice\":9741,\"recipe\":null},\"quantity\":4}" +
                        "]}"));
    }
}
