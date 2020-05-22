package com.example.demo.controller;

import com.example.demo.dto.authentication.UserSignInResponse;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.*;
import com.example.demo.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
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
public abstract class AbstractControllerTest {
    protected final static long ID = 3;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
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


    protected String signIn(Roles roles) throws Exception {
        AuthInfoEntity infoEntity = new AuthInfoEntity();
        infoEntity.setLogin("vasya@email.com");
        infoEntity.setPassword(passwordEncoder.encode("qwerty"));
        infoEntity.setRoles(Collections.singleton(roles));

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

    protected AuthInfoEntity createAuthInfo(Roles roles) {
        final UserEntity user = new UserEntity();
        user.setEmail("vasya@email.com");

        final AuthInfoEntity authInfo = new AuthInfoEntity();
        authInfo.setLogin(user.getEmail());
        authInfo.setPassword(passwordEncoder.encode("qwerty"));
        authInfo.setUser(user);
        authInfo.setRoles(Collections.singleton(roles));
        return authInfo;
    }

    protected AuthInfoEntity createAuthInfo(Roles roles, UserEntity user) {
        final AuthInfoEntity authInfo =createAuthInfo(roles);
        authInfo.setRoles(Collections.singleton(roles));
        authInfo.setUser(user);
        return authInfo;
    }

}
