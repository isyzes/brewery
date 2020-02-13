package com.example.demo.controller;

import com.example.demo.dto.UserSignInResponse;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.security.LoadUserDetailService;
import com.example.demo.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasLength;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @MockBean
    protected BeerRepository beerRepository;

    @MockBean
    protected WarehouseRepository warehouseRepository;

    @SpyBean
    protected LoadUserDetailService loadUserDetailService;


    protected String signIn(Roles roles) throws Exception {
        final User user = new User("vasya@email.com", passwordEncoder.encode("qwerty"),
                List.of(new SimpleGrantedAuthority("ROLE_" + roles.name())));

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
