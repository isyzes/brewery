package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testEmployeeTakeIsOk() throws Exception {

        mockMvc.perform(post("/staff/employee/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Ivanov Ivan Ivanovich\", \"department\" : \"Production\", \"wages\" : 2020}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"id\" : 1\n" +
                        "}"));
    }

    @Test
    public void testEmployeeToDismissIsOk() throws Exception {

        mockMvc.perform(put("/staff/employee/to-dismiss/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStaffListIsOk() throws Exception {
        mockMvc.perform(get("/staff/list"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{" +
                            "\"id\":5," +
                            "\"name\":\"Adam Gordon\"," +
                            "\"department\":\"Production\"," +
                            "\"wages\":2500.0," +
                            "\"dateStart\":\"15.01.2018\"," +
                            "\"dateEnd\":null," +
                            "\"works\":true" +
                        "}," +
                        "{" +
                            "\"id\":2," +
                            "\"name\":\"Carla Williams\"," +
                            "\"department\":\"Production\"," +
                            "\"wages\":5070.0," +
                            "\"dateStart\":\"15.01.2018\"," +
                            "\"dateEnd\":null," +
                            "\"works\":true" +
                        "}," +
                        "{" +
                            "\"id\":4," +
                            "\"name\":\"Boris Jones\"," +
                            "\"department\":\"Production\"," +
                            "\"wages\":1500.0," +
                            "\"dateStart\":\"15.01.2018\"," +
                            "\"dateEnd\":\"14.10.2019\"," +
                            "\"works\":false" +
                        "}]"));
    }
}
