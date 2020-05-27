package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.example.demo.security.Roles.EMPLOYEE;
import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest extends AbstractControllerTest {

    @Test
    void testEmployeeTakeIsOk() throws Exception {
        // given
        final UserEntity request = ControllerMockData.getNewEmployeeRequestEntity();
        final UserEntity response = ControllerMockData.getNewEmployeeResponseEntity();
        given(userRepository.save(request)).willReturn(response);
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(post("/staff/employee/create").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fio\" : \"Ivanov Ivan Ivanovich\", \"department\" : \"Production\", \"wages\" : 2020}"))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"id\" : 0\n" +
                        "}"));
        verify(userRepository, Mockito.times(1)).save(response);
    }

    @Test
    void testEmployeeToDismissIsOk() throws Exception {
        // given
        final UserEntity save = ControllerMockData.getAuthNewEmployeeToDismiss();
        given(userRepository.findById(ID)).willReturn(ControllerMockData.getOptionalEmployeeEntity());
        given(userRepository.save(save)).willReturn(save);
        final String token = signIn(MANAGER);
        // when
        mockMvc.perform(put("/staff/employee/to-dismiss/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
        verify(userRepository, Mockito.times(1)).findById(ID);
        verify(userRepository, Mockito.times(1)).save(save);
    }

    @Test
    void testGetStaffListIsOk() throws Exception {
        // given
        final String token = signIn(MANAGER);
        given(authInfoRepository.findByRoles(EMPLOYEE)).willReturn(ControllerMockData.getAuthInfoEntity());
        // when
        mockMvc.perform(get("/staff/list").header("Authorization", token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{" +
                            "\"id\":5," +
                            "\"fio\":\"Adam Gordon\"," +
                            "\"department\":\"Production\"," +
                            "\"wages\":2500.0," +
                            "\"dateStart\":\"15.01.2018\"," +
                            "\"dateEnd\":null," +
                            "\"works\":true" +
                        "}," +
                        "{" +
                            "\"id\":2," +
                            "\"fio\":\"Carla Williams\"," +
                            "\"department\":\"Production\"," +
                            "\"wages\":5070.0," +
                            "\"dateStart\":\"15.01.2018\"," +
                            "\"dateEnd\":null," +
                            "\"works\":true" +
                        "}," +
                        "{" +
                            "\"id\":4," +
                            "\"fio\":\"Boris Jones\"," +
                            "\"department\":\"Production\"," +
                            "\"wages\":1500.0," +
                            "\"dateStart\":\"15.01.2018\"," +
                            "\"dateEnd\":\"14.10.2019\"," +
                            "\"works\":false" +
                        "}]"));
        verify(authInfoRepository, Mockito.times(1)).findByRoles(EMPLOYEE);
    }
}
