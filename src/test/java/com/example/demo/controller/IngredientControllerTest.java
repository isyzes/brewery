package com.example.demo.controller;

import com.example.demo.dto.UserSignInResponse;
import com.example.demo.mockdata.ControllerMockData;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.security.LoadUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.demo.security.Roles.MANAGER;
import static org.hamcrest.Matchers.hasLength;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IngredientControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected IngredientRepository ingredientRepository;
    @SpyBean
    protected LoadUserDetailService loadUserDetailService;

    @Test
    public void testBuyIngredientIsOk() throws Exception {

        ingredientRepository.save(ControllerMockData.getNewIngredient());

        final String token = signIn();

        mockMvc.perform(put("/ingredients/buy").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idIngredient\" : \"1\", \"milligramsInStock\" : 2551}"))

                .andExpect(status().isOk());
    }

    private String signIn() throws Exception {
        final User user = new User("vasya@email.com", passwordEncoder.encode("qwerty"),
                List.of(new SimpleGrantedAuthority("ROLE_" + MANAGER.name())));

        willReturn(user).given(loadUserDetailService).loadUserByUsername("vasya@email.com");

        final String response = mockMvc.perform(post("/employee/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"qwerty\"\n" +
                        "}"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("token", hasLength(144)))
                .andReturn().getResponse().getContentAsString();

        return "Bearer " + objectMapper.readValue(response, UserSignInResponse.class).getToken();
    }


}
