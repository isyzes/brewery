package com.example.demo.controller;

import com.example.demo.entity.IngredientEntity;
import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Optional;

import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IngredientControllerTest extends AbstractControllerTest {

    @Test
    void testBuyIngredientIsOk() throws Exception {
        // given
        final IngredientEntity save = ControllerMockData.getNewIngredient(650402);
        given(ingredientRepository.findById(3L)).willReturn(ControllerMockData.getNewOptionalIngredient());
        given(ingredientRepository.save(save)).willReturn(save);
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/ingredients/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idIngredient\" : \"3\", \"milligramsInStock\" : 2551}"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{\"idIngredient\":3,\"totalMilligramsInStock\":650402}"));
        verify(ingredientRepository, Mockito.times(1)).findById(3L);
        verify(ingredientRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testBuyIngredientIsBadRequest() throws Exception {
        // given
        given(ingredientRepository.findById(2L)).willReturn(Optional.empty());
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/ingredients/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idIngredient\" : \"2\", \"milligramsInStock\" : 2551}"))
                // then
                .andExpect(status().isBadRequest());
        verify(ingredientRepository, Mockito.times(1)).findById(2L);
    }
}
