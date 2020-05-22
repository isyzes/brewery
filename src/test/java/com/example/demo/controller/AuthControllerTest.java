package com.example.demo.controller;

import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.security.Roles.CONSUMER;
import static com.example.demo.security.Roles.EMPLOYEE;
import static org.hamcrest.Matchers.hasLength;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest extends AbstractControllerTest {

    @Test
    void testEmployeeSignUpIsCreated() throws Exception {
        // given
        final UserEntity mockUser = ControllerMockData.getAuthNewConsumerEntity();
        final AuthInfoEntity mockAuthInfo = createAuthInfo(CONSUMER, mockUser);
        given(userRepository.save(mockUser)).willReturn(mockUser);
        given(authInfoRepository.save(mockAuthInfo)).willReturn(mockAuthInfo);
        AuthInfoEntity entity = new AuthInfoEntity();
        entity.setLogin("vasya@email.com");
        entity.setPassword(passwordEncoder.encode("qwerty"));
        // when
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"qwerty\",\n" +
                        "  \"fio\" : \"Пупкин Василий Иванович\",\n" +
                        "  \"birthDate\" : \"19.01.1995\"\n" +
                        "}"))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token", hasLength(144)));
        verify(userRepository, Mockito.times(1)).save(mockUser);
        verify(authInfoRepository).save(ArgumentMatchers.any(AuthInfoEntity.class));
    }

    @Test
    void testEmployeeSignUpWhenUserAlreadyExisted() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"qwerty\",\n" +
                        "  \"fio\" : \"Пупкин Василий Иванович\",\n" +
                        "  \"birthDate\" : \"19.01.1995\",\n" +
                        "}"))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmployeeSignInIsOk() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"qwerty\"\n" +
                        "}"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("token", hasLength(144)));
    }

    @Test
    void testEmployeeSignInWithWrongPassword() throws Exception {
        // given
        signIn(CONSUMER);
        // when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"wrongPassword\"\n" +
                        "}"))
                // then
                .andExpect(status().isForbidden());
    }

    @Test
    void testEmployeeSignInWithWrongEmail() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"noSuchVasyavasya@email.com\",\n" +
                        "  \"password\" : \"wrongPassword\"\n" +
                        "}"))
                // then
                .andExpect(status().isForbidden());
    }
}