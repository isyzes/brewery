package com.example.demo;

import com.example.demo.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class DemoApplicationTests extends AbstractControllerTest {

    @Test
    void contextLoads() {
    }

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/test")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }
}
