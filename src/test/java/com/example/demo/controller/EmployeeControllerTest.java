package com.example.demo.controller;

import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.example.demo.security.Roles.EMPLOYEE;
import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest extends AbstractControllerTest {

    @Test
    public void testEmployeeTakeIsOk() throws Exception {
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        final String token = signIn(MANAGER);

        mockMvc.perform(post("/staff/employee/created").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Ivanov Ivan Ivanovich\", \"department\" : \"Production\", \"wages\" : 2020}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"id\" : 0\n" +
                        "}"));
    }

    @Test
    public void testEmployeeToDismissIsOk() throws Exception {
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        final String token = signIn(MANAGER);

        given(userRepository.findById(ControllerMockData.ID)).willReturn(ControllerMockData.getOptionalEmployeeEntity());

        mockMvc.perform(put("/staff/employee/to-dismiss/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStaffListIsOk() throws Exception {
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        final String token = signIn(MANAGER);
        given(userRepository.findAllByUserRole(EMPLOYEE)).willReturn(ControllerMockData.getEmployeeEntities());
        mockMvc.perform(get("/staff/list").header("Authorization", token))
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
    }
}
