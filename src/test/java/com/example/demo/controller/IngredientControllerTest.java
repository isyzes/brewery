package com.example.demo.controller;

import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IngredientControllerTest extends AbstractControllerTest {
    @Test
    public void testUpdatedBeerIsOk() throws Exception {
        final String token = signIn(MANAGER);

        given(ingredientRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getNewOptionalIngredient());

        mockMvc.perform(put("/ingredients/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idIngredient\" : \"3\", \"milligramsInStock\" : 2551}"))

                .andExpect(status().isOk());
    }
}
