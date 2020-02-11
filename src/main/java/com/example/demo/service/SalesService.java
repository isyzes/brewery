package com.example.demo.service;

import com.example.demo.dto.OneOrder;
import com.example.demo.dto.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesService {
    public Order buyBeer(List<OneOrder> orders, long idConsumers) {
        Order order = new Order(orders);
        order.setId(4);
        return order;
    }
}
