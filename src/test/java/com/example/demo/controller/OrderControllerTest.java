package com.example.demo.controller;

import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.mockdata.ControllerMockData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static com.example.demo.security.Roles.MANAGER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OrderControllerTest extends AbstractControllerTest {

    @Test
    void testEmployeeSignUpIsCreated() throws Exception {
        // given
        final BeerEntity firstSaveBeer = ControllerMockData.getSaveBeer(3, 6225- 5);
        final BeerEntity secondSaveBeer = ControllerMockData.getSaveBeer(4, 6225- 4);
        final OrderItemEntity firstSaveItem = ControllerMockData.getNewOrderItemEntity(firstSaveBeer, 5);
        final OrderItemEntity secondSaveItem = ControllerMockData.getNewOrderItemEntity(secondSaveBeer, 4);
        final List<OrderEntity> save = Collections.singletonList(ControllerMockData.getSaveOrderEntity(firstSaveItem, secondSaveItem));
        
        given(userRepository.findAllByEmail("vasya@email.com")).willReturn(ControllerMockData.getAuthNewConsumerEntity());
        given(orderRepository.findAllByCreatedAfter(any())).willReturn(save);
        final String token = signIn(MANAGER);
        
        // when
        mockMvc.perform(get("/orders/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":0,\"price\":26.28,\"user_id\":4,\"item\":[{\"id\":0,\"beer_id\":3,\"liters\":5,\"order_id\":0},{\"id\":0,\"beer_id\":4,\"liters\":4,\"order_id\":0}]}]"));
        verify(orderRepository, times(1)).findAllByCreatedAfter(any());
    }
}