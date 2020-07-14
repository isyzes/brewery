package com.example.demo.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrder {
    private List<OrderItem> items;
}

