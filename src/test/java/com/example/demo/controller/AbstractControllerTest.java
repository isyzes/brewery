package com.example.demo.controller;

import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.mockdata.ControllerMockData;
import com.example.demo.repository.*;
import com.example.demo.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasLength;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public abstract class AbstractControllerTest extends ControllerMockData {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected AuthInfoRepository authInfoRepository;
    @MockBean
    protected BeerRepository beerRepository;
    @MockBean
    protected OrderRepository orderRepository;
    @MockBean
    protected IngredientRepository ingredientRepository;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected OrderItemRepository orderItemRepository;
    @MockBean
    protected RecipeRepository recipeRepository;

    protected String signIn(Roles roles) throws Exception {
        final AuthInfoEntity infoEntity = createAuthInfo(roles);

        given(authInfoRepository.findByLogin("vasya@email.com")).willReturn(Optional.of(infoEntity));

        final String response = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"qwerty\"\n" +
                        "}"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("token", hasLength(144)))
                .andReturn().getResponse().getContentAsString();

        verify(authInfoRepository, Mockito.times(2)).findByLogin("vasya@email.com");

        return "Bearer " + objectMapper.readValue(response, UserSignInResponse.class).getToken();
    }
}
