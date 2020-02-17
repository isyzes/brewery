package com.example.demo.controller;

import static com.example.demo.security.Roles.EMPLOYEE;
import static com.example.demo.security.Roles.MANAGER;
import static org.hamcrest.Matchers.hasLength;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


public class AuthControllerTest extends AbstractControllerTest {

    @Test
    public void testEmployeeSignUpIsCreated() throws Exception {
        // given
        willReturn(Optional.empty(), Optional.of(createAuthInfo(EMPLOYEE))).given(authInfoRepository)
                .findByLogin("vasya@email.com");
        // when
        mockMvc.perform(post("/employee/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"qwerty\",\n" +
                        "  \"fio\" : \"Пупкин Василий Иванович\",\n" +
                        "  \"gender\" : \"MALE\", \n" +
                        "  \"birthDate\" : \"19.01.1995\",\n" +
                        "  \"info\" : \"Молодой инженер\" \n" +
                        "}"))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token", hasLength(144)));
    }

    @Test
    public void testEmployeeSignUpWhenUserAlreadyExisted() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/employee/sign-up")
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
    public void testEmployeeSignInIsOk() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/employee/sign-in")
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
    public void testEmployeeSignInWithWrongPassword() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/employee/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"vasya@email.com\",\n" +
                        "  \"password\" : \"wrongPassword\"\n" +
                        "}"))
                // then
                .andExpect(status().isForbidden());
    }

    @Test
    public void testEmployeeSignInWithWrongEmail() throws Exception {
        // given
        signIn(EMPLOYEE);
        // when
        mockMvc.perform(post("/employee/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"noSuchVasyavasya@email.com\",\n" +
                        "  \"password\" : \"wrongPassword\"\n" +
                        "}"))
                // then
                .andExpect(status().isForbidden());
    }
}
